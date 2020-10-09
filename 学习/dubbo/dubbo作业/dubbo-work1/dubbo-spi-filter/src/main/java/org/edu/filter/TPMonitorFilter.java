package org.edu.filter;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


@Activate(group = {CommonConstants.CONSUMER})
public class TPMonitorFilter implements Filter,Runnable {

    private ConcurrentHashMap<String ,CopyOnWriteArrayList<Long>> map  = new ConcurrentHashMap<String ,CopyOnWriteArrayList<Long>>();

    public TPMonitorFilter(){
        Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(this, 10, 5, TimeUnit.SECONDS);
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
         Long start  = System.currentTimeMillis();
        // 执行方法
        Result result = invoker.invoke(invocation);
        Long  end  = System.currentTimeMillis();
        Long time = end - start;
        if (map == null){
            CopyOnWriteArrayList<Long> list = new  CopyOnWriteArrayList<Long>();
            list.add(time);
            map.put(invocation.getMethodName(),list);
        }else {
            CopyOnWriteArrayList<Long> list = map.get(invocation.getMethodName());
            if (list == null || list.size() == 0){
                list = new  CopyOnWriteArrayList<Long>();
            }
            list.add(time);
            map.put(invocation.getMethodName(),list);
        }
        return  result;

    }


    @Override
    public void run() {
         System.out.println("--------------定时监控------------------");
        try {
            double tp99 = 0.99;
            double tp90 = 0.90;
            for (String key :map.keySet()){
                CopyOnWriteArrayList<Long> list  =  map.get(key);
                if (list != null){
                    double t99 = (Double.valueOf(list.size())  * tp99);
                    double t90 = (Double.valueOf(list.size())  * tp90);
                    System.out.println("方法名："+key+" 的TP99值为："+list.get((int) t99));
                    System.out.println("方法名："+key+" 的TP90值为："+ list.get((int) t90));
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            map.clear();
        }

    }


}
