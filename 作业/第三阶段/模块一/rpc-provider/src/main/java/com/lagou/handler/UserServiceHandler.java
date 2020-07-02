package com.lagou.handler;

import com.lagou.common.RpcRequest;
import com.lagou.service.UserServiceImpl;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutorGroup;

import java.lang.reflect.Method;

/**
 * 自定义的业务处理器
 */
public class UserServiceHandler extends ChannelInboundHandlerAdapter {

    //当客户端读取数据时,该方法会被调用
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg instanceof RpcRequest){
            //msg强转为RpcRequest对象
            RpcRequest rpcRequest = (RpcRequest) msg;

            //加载 IUserService
            Class clazz = Class.forName(rpcRequest.getClassName());

            //获取远程调用方法的参数
            Object[] parameters = rpcRequest.getParameters();

            //获取方法名
            String methodName = rpcRequest.getMethodName();

            //在已知子类的情况下通过强制转换的方式获取子类的实例
            Class subclass = UserServiceImpl.class.asSubclass(clazz);

            //通过反射的方式实现
            Object object = subclass.newInstance();

            Method method = clazz.getMethod(methodName, rpcRequest.getParameterTypes());

            Object result = method.invoke(object, parameters);

            ctx.writeAndFlush("success");
        }


    }
}
