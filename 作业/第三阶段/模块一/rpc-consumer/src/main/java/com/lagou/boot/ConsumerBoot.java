package com.lagou.boot;

import com.lagou.client.RPCConsumer;
import com.lagou.common.RpcRequest;
import com.lagou.service.IUserService;

import java.util.UUID;

public class ConsumerBoot {

    public static void main(String[] args) throws InterruptedException {

        //1.创建代理对象
        IUserService service = (IUserService) RPCConsumer.createProxy(IUserService.class, new RpcRequest());

        //2.循环给服务器写数据
        while (true){
            String result = service.sayHello("are you ok !!");
            System.out.println(result);
            Thread.sleep(2000);
        }

    }
}
