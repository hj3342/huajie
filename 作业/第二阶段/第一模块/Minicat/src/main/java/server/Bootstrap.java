package server;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Minicat的主类
 */
public class Bootstrap {

    /**定义socket监听的端口号*/
    private int port = 8080;

    /**定义主机名*/
    private String hostName ;

    /**定义Engine映射*/
    LagouMapper mapper = new LagouMapper();

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }


    /**
     * Minicat启动需要初始化展开的一些操作
     */
    public void start() throws Exception {
        //加载解析serser.xml
        try {
            //server
            InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("server1.xml");
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(resourceAsStream);
            Element rootElement = document.getRootElement();
            //service
            List<Element> serviceNodes = rootElement.selectNodes("//Service");
            for (int i = 0; i < serviceNodes.size(); i++) {
                //****************************Connector****************************
                List<Element> connertorNodes = serviceNodes.get(i).selectNodes("//Connector");
                for (int j = 0; j <connertorNodes.size() ; j++) {
                    port =  Integer.valueOf(connertorNodes.get(j).attributeValue("port"));
                    System.out.println("解析端口："+port);
                }
                //engine
                Node engineNode = serviceNodes.get(i).selectSingleNode("//Engine");
                //host
                List<Element> hostNodes = engineNode.selectNodes("//Host");
                for (int j = 0; j < hostNodes.size(); j++) {
                    LagouHost host = new LagouHost();
                    hostName = hostNodes.get(j).attributeValue("name");
                    System.out.println("解析ip地址："+hostName);
                    String appBase = hostNodes.get(j).attributeValue("appBase");
                    System.out.println("解析appBase绝对路径"+appBase);
                    File appBaseFolder = new File(appBase);
                    File[] files = appBaseFolder.listFiles();
                    for(File file: files) {
                        if(file.isDirectory()) {
                            //Context
                            LagouContext context = new LagouContext();
                            String contextPath = file.getName();
                            context.setPath(contextPath);
                            // Wrappers
                            File web = new File(file,"web.xml");
                            List<LagouWrapper> list = loadServlet(web.getAbsolutePath());
                            context.setLagouWrappers(list);
                            host.getLagouContexts().add(context);
                        }
                    }

                    host.setName(hostName);
                    mapper.getLagouHosts().add(host);
                }
            }



        } catch (DocumentException e) {
            e.printStackTrace();
        }


        // 定义一个线程池
        int corePoolSize = 10;
        int maximumPoolSize =50;
        long keepAliveTime = 100L;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(50);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();


        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                workQueue,
                threadFactory,
                handler
        );


        /*
            完成Minicat 1.0版本
            需求：浏览器请求http://localhost:8080,返回一个固定的字符串到页面"Hello Minicat!"
         */
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("=====>>>Minicat start on port：" + port);

        System.out.println("=========>>>>>>使用线程池进行多线程改造");
        /*
            多线程改造（使用线程池）
         */
        while(true) {

            Socket socket = serverSocket.accept();
            RequestProcessor requestProcessor = new RequestProcessor(socket, mapper);
            //requestProcessor.start();
            threadPoolExecutor.execute(requestProcessor);
        }



    }




    /**
     * 加载解析web.xml，初始化Servlet
     */
    private List<LagouWrapper> loadServlet(String webXmlPath) throws FileNotFoundException {

        List<LagouWrapper> lagouWrappers = new ArrayList<>();
        InputStream resourceAsStream = new FileInputStream(new File(webXmlPath));
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(resourceAsStream);
            Element rootElement = document.getRootElement();
            ClassLoaderExpand classLoaderExpand = new ClassLoaderExpand();
            List<Element> selectNodes = rootElement.selectNodes("//servlet");
            for (int i = 0; i < selectNodes.size(); i++) {
                Element element =  selectNodes.get(i);
                // <servlet-name>lagou</servlet-name>
                Element servletnameElement = (Element) element.selectSingleNode("servlet-name");
                String servletName = servletnameElement.getStringValue();
                // <servlet-class>server.LagouServlet</servlet-class>
                Element servletclassElement = (Element) element.selectSingleNode("servlet-class");
                String servletClass = servletclassElement.getStringValue();


                // 根据servlet-name的值找到url-pattern
                Element servletMapping = (Element) rootElement.selectSingleNode("/web-app/servlet-mapping[servlet-name='" + servletName + "']");
                // /lagou
                String urlPattern = servletMapping.selectSingleNode("url-pattern").getStringValue();

                LagouWrapper wrapper = new LagouWrapper();
                wrapper.setUrlPattern(urlPattern);
                Class<?> aClass = classLoaderExpand.findClass(webXmlPath.replace("web.xml", "").replace("\\","/"),servletClass);
                wrapper.setServlet((HttpServlet) Class.forName(servletClass).newInstance());
                lagouWrappers.add(wrapper);
            }



        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return lagouWrappers;
    }


    /**
     * Minicat 的程序启动入口
     * @param args
     */
    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        try {
            // 启动Minicat
            bootstrap.start();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
