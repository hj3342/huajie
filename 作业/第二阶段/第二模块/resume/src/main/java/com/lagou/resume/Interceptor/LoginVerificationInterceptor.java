package com.lagou.resume.Interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

public class LoginVerificationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
         //获取session
         HttpSession session = request.getSession();

        Enumeration<String> attrs = session.getAttributeNames();
        while(attrs.hasMoreElements()){
            // 获取session键值
            String name = attrs.nextElement().toString();
            // 根据键值取session中的值
            Object vakue = session.getAttribute(name);
            // 打印结果
            System.out.println("------" + name + ":" + vakue +"--------\n");

        }

         String user =  (String) session.getAttribute("USER_SESSION");
         //判断session中是否有用户数据，如果有，则返回true，继续向下执行
         if (user != null) {
                return true;
            }

         response.sendRedirect(request.getContextPath() + "/login");
         return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }
}
