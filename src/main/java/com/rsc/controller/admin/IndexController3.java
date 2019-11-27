package com.rsc.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @ClassName:IndexController3
 * @Description:TODO
 * @Author:chenyx
 * @Date:Create in  2019/11/20 0:52
 **/
@RequestMapping("rsc/admin")
@Controller
public class IndexController3 {

    //返回管理员的开始上班(分配工作)页面
    @GetMapping("awork")
    public String awork(HttpSession session) {
        return "admin/work";
    }

}
