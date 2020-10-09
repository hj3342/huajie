package org.edu;

import org.apache.dubbo.config.annotation.Reference;
import org.edu.service.HelloService;
import org.springframework.stereotype.Component;

@Component
public class ConsumerComponent {

    @Reference
    private HelloService helloService;

    public String sayHello(String name) {
        return helloService.sayHello(name);
    }

    public String sayHelloA(String name) {
        System.out.println("sayHelloA");
        return helloService.sayHelloA(name);
    }

    public String sayHelloB(String name) {
        System.out.println("sayHelloB");
        return helloService.sayHelloB(name);
    }

    public String sayHelloC(String name) {
        System.out.println("sayHelloC");
        return helloService.sayHelloC(name);
    }

}
