package org.edu;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class DubboConsumerMain {

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 10000,
            5, TimeUnit.SECONDS, new ArrayBlockingQueue<>(5000));

    public static void main(String[] args) throws IOException, InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsumerConfiguration.class);
        context.start();
        ConsumerComponent service = context.getBean(ConsumerComponent.class);

            System.in.read();
            try {
                while (true) {
                    Thread.sleep(300);
                    executor.execute(() -> service.sayHelloA("world"));
                    executor.execute(() -> service.sayHelloB("world"));
                    executor.execute(() -> service.sayHelloC("world"));
                }
        } catch (Exception e) {
                e.printStackTrace();
            }
    }

    @Configuration
    @PropertySource("classpath:/dubbo-consumer.properties")
    @ComponentScan("org.edu")
    @EnableDubbo
    static class ConsumerConfiguration {

    }
}
