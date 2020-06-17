package server;

import java.io.InputStream;
import java.net.Socket;
import java.util.List;

public class RequestProcessor extends Thread {

    private Socket socket;
    private LagouMapper engine;

    public RequestProcessor(Socket socket, LagouMapper engine) {
        this.socket = socket;
        this.engine = engine;
    }

    @Override
    public void run() {
        try{
            InputStream inputStream = socket.getInputStream();

            // 封装Request对象和Response对象
            Request request = new Request(inputStream);
            Response response = new Response(socket.getOutputStream());
            Servlet servlet = getServlet(request.getUrl());

            // 静态资源处理
            if(servlet == null) {
                response.outputHtml(request.getUrl());
            }else{
                // 动态资源servlet请求
                servlet.service(request,response);
            }

            socket.close();

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 根据url从engine中获取servlet
     * @param url
     * @return
     */
    private Servlet getServlet(String url) {
        String[] split = url.split("/");
        String contextFlag = split[1];

        List<LagouContext> contexts = engine.getLagouHosts().get(0).getLagouContexts();
        for(LagouContext context: contexts) {
            if(contextFlag.equalsIgnoreCase(context.getPath())) {
                List<LagouWrapper> wrappers = context.getLagouWrappers();
                for(LagouWrapper wrapper:wrappers) {
                    if(wrapper.getUrlPattern().equalsIgnoreCase(url.trim())) {
                        return wrapper.getServlet();
                    }
                }
            }
        }
        return null;
    }
}
