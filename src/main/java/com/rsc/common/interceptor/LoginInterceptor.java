package com.rsc.common.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @ClassName:LoginInterceptor
 * @Description:TODO 登录拦截器
 * @Author:chenyx
 * @Date:Create in  2019/11/21 11:01
 **/
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("已经进入登录拦截器");
        String url = request.getRequestURI();
        System.out.println("url------:" + url);
        HttpSession session = request.getSession();

        if (url.contains("customer")) {
            if (null != session.getAttribute("customer")) {
                return true;
            } else {
                String tourl = "/rsc/customer/clogin";
                response.sendRedirect(tourl);
                return false;
            }
        } else if (url.contains("postman")) {
            if (null != session.getAttribute("postman")) {
                return true;
            } else {
                String tourl = "/rsc/postman/plogin";
                response.sendRedirect(tourl);
                return false;
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
