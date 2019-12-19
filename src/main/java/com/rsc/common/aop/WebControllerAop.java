package com.rsc.common.aop;

import com.rsc.entity.Postman;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @ClassName:WebControllerAop2
 * @Description:TODO
 * @Author:陈一心
 * @Date:Create in  2019/9/12 17:32
 **/
@Component
@Aspect
public class WebControllerAop {


    @Pointcut("execution(* com.rsc.service.PostmanService.downExcelOfReceiveMail(..))")
    public void executeService1() {

    }


    @Pointcut("execution(* com.rsc.service.PostmanService.downExcelOfAssigneMail(..))")
    public void executeService2() {

    }


    @Before("executeService1()&&args(session,response)")
    public String doBeforeAdivce1(JoinPoint joinPoint, HttpSession session, HttpServletResponse response){
        System.out.println("==========doBeforeAdivce1==========");
        Postman postman = (Postman) session.getAttribute("postman");
        if (postman == null) {
            System.out.println("==========空工号，没登录！==========");
            return "postman/login";
        }
        return null;
    }


    @Before("executeService2()&&args(session,response)")
    public String doBeforeAdivce2(JoinPoint joinPoint, HttpSession session, HttpServletResponse response){
        System.out.println("==========doBeforeAdivce2==========");
        Postman postman = (Postman) session.getAttribute("postman");
        if (postman == null) {
            System.out.println("==========空工号，没登录！==========");
            return "postman/login";
        }
        return null;
    }
}