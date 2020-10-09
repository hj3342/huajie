package org.edu.web;


import org.edu.ConsumerComponent;
import org.edu.filter.IpThreadLoacal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class LoginController {



    @Autowired
    ConsumerComponent component;

    @RequestMapping(value = "/",method = {RequestMethod.GET})
    public String login(HttpServletRequest request) throws IOException {
        String hello ="";
        try {
            IpThreadLoacal.setIp(request.getRemoteAddr());
             hello = component.sayHello( "word");
            System.out.println("result :" + hello);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hello;
    }



}
