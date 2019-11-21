package com.rsc.controller.postman;

import com.rsc.service.PostmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @ClassName:CustomerController
 * @Description:TODO
 * @Author:chenyx
 * @Date:Create in  2019/11/15 19:59
 **/
@RequestMapping("rsc/postman")
@Controller
public class PostmanController {

    @Autowired
    private PostmanService postmanService;

    //邮差提交的登录
    @PostMapping("ptologin")
    public String postmanToLogin(@RequestParam String phone, @RequestParam String password, HttpSession session) {
        return postmanService.postmanToLogin(phone, password, session);
    }

    //邮差去签到
    @GetMapping("pduty")
    public String postmanToDuty(HttpSession session) {
        return postmanService.postmanAddWorkload(session);
    }

    //邮差查看要取的邮件
    @GetMapping("preceive")
    public String postmanToReceive(HttpSession session,@RequestParam int page){
        return postmanService.postmanToReceive(session,page,1,"postman/receive");
    }

    //邮差一键全部接单(接全部收件)
    @GetMapping("ptoallreceive")
    public String postmanToAllReceive(HttpSession session){
        return postmanService.postmanToAllReceive(session);
    }

    //邮差确定邮件是否收取成功
    @RequestMapping("pdeterminereceive")
    public String postmanDetermineReceive(HttpSession session,@RequestParam int page){
        return postmanService.postmanToReceive(session,page,2,"postman/determineReceive");//可以公用
    }

    //邮差确定某邮件收件故障
    @RequestMapping("receivefault")
    public String postmanReceivefault(HttpSession session, @RequestParam int mailId, @RequestParam String season, @RequestParam int page, Model model){
        return postmanService.postmanReceivefault(session,mailId,season,page,model);
    }
}
