package com.rsc.controller.postman;

import com.rsc.entity.Postman;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @ClassName:IndexController
 * @Description:TODO 邮差页面跳转
 * @Author:chenyx
 * @Date:Create in  2019/11/15 20:00
 **/
@RequestMapping("rsc/postman")
@Controller
public class IndexController2 {

    //返回邮差使用的登录页面
    @GetMapping("plogin")
    public String postmanLogin(){
        return "postman/login";
    }

    //返回邮差使用的主页
    @GetMapping("pindex")
    public String postmanIndex(HttpSession session){
        Postman postman = (Postman) session.getAttribute("postman");
        if (postman == null) {
            System.out.println("空工号，没登录！");
            return "postman/login";
        } else {
            System.out.println("返回邮差使用的主页" + postman.getId());
        }
        session.setAttribute("postman",postman);
        return "postman/index";
    }



}
