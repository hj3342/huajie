package org.edu.filter;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

@Activate(group = {CommonConstants.CONSUMER,CommonConstants.PROVIDER})
public class TransportIPFilter implements Filter {

        @Override
        public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

            if(RpcContext.getContext().isConsumerSide()){
                //设置 访问IP
                RpcContext.getContext().setAttachment("ip",IpThreadLoacal.getIp());
                System.out.println("IP: "+RpcContext.getContext().getAttachment("ip"));
            }
            if(RpcContext.getContext().isProviderSide()){
                //获取 访问IP
                String ip=RpcContext.getContext().getAttachment("ip");
                System.out.println("Print IP:"+ip);
            }
            // 执行方法
            return  invoker.invoke(invocation);
        }
}
