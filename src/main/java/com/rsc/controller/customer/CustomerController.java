package com.rsc.controller.customer;

import com.rsc.entity.Customer;
import com.rsc.entity.Mail;
import com.rsc.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @ClassName:CustomerController
 * @Description:TODO
 * @Author:chenyx
 * @Date:Create in  2019/11/15 19:59
 **/
@RequestMapping("rsc/customer")
@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    //用户提交的注册
    @PostMapping("toregister")
    public String customerToRegister(@ModelAttribute Customer customer, HttpSession session) {
       return customerService.customerToRegister(customer,session);
    }

    //用户提交的登录
    @PostMapping("ctologin")
    public String customerToLogin(@RequestParam String phone, @RequestParam String password, HttpSession session) {
        return customerService.customerToLogin(phone, password, session);
    }

    //用户提交的快递
    @PostMapping("postcmail")
    public String customerToDelivery(@ModelAttribute Mail mail,HttpSession session){
        return customerService.customerToDelivery(mail,session);
    }

    //用户查看/确定邮件是否已经收取/付款：返回”准备收件“状态+“正在收件”+”等待分配“状态的单
    @GetMapping("cCheckOrDetermine")
    public String customerCheckOrDetermine(HttpSession session,@RequestParam int page){
        return customerService.customerCheckOrDetermine(session,page);
    }

    //用户确定邮件已经被邮差收取
    @PostMapping("cDetermineMail")
    public String customerDetermineMail(HttpSession session,@RequestParam int mailId){
        return customerService.customerDetermineMail(session,mailId);
    }

    //用户查看已经"收件完成"的寄件：返回”收件完成“状态的单
    @GetMapping("selectmail")
    public String customerSelectMail(HttpSession session,@RequestParam int page){
        return customerService.customerSelectMail(session,page);
    }

    //用户查看"收件/派件不成功"的寄件：返回”收件/派件不成功“状态的单
    @GetMapping("selectfaultmail")
    public String customerSelectFaultMail(HttpSession session,@RequestParam int page){
        return customerService.customerSelectFaultMail(session,page);
    }

}
