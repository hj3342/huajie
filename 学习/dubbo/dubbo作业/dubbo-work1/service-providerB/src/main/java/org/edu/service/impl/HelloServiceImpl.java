package org.edu.service.impl;

import org.edu.service.HelloService;

import java.util.concurrent.TimeUnit;

public class HelloServiceImpl implements HelloService {

    public String sayHello(String name) {
        return "hello:"+name;
    }

    @Override
    public String sayHelloA(String name) {
        try {

            int sleppTime = (int) (Math.random() * 100);
            TimeUnit.MILLISECONDS.sleep(sleppTime);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return "A :"+name;
    }
    @Override
    public String sayHelloB(String name) {
        try {

            int sleppTime = (int) (Math.random() * 100);
            TimeUnit.MILLISECONDS.sleep(sleppTime);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "B :"+name;
    }
    @Override
    public String sayHelloC(String name) {
        try {

            int sleppTime = (int) (Math.random() * 100);
            TimeUnit.MILLISECONDS.sleep(sleppTime);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "C :"+name;
    }
}
