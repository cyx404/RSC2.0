package com.rsc.service;

import com.rsc.entity.Customer;
import com.rsc.entity.Mail;

import javax.servlet.http.HttpSession;

public interface CustomerService {

    /**
     * @Title customerToRegister
     * @Description: TODO 用户注册
     * @param customer
     * @param session
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/11/16  1:09
     **/
    String customerToRegister(Customer customer, HttpSession session);

    /**
     * @Title customerToLogin
     * @Description: TODO 验证登录
     * @param phone
     * @param password
     * @param session
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/11/16  0:54
     **/
    String customerToLogin(String phone, String password, HttpSession session);

    /**
     * @Title customerToDelivery
     * @Description: TODO 客户寄快递
     * @param mail
     * @param session
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/11/16  22:01
     **/
    String customerToDelivery(Mail mail, HttpSession session);

    /**
     * @Title customerCheckOrDetermine
     * @Description: TODO 用户查看/确定邮件是否已经收取/付款
     * @param session
     * @param page
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/11/17  14:07
     **/
    String customerCheckOrDetermine(HttpSession session, int page);

    /**
     * @Title customerDetermineMail
     * @Description: TODO 用户确定邮件已经被邮差收取
     * @param session
     * @param mailId
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/11/17  15:22
     **/
    String customerDetermineMail(HttpSession session, int mailId);

    /**
     * @Title customerSelectMail
     * @Description: TODO 用户查看已经收件完成的寄件
     * @param session
     * @param page
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/11/17  17:14
     **/
    String customerSelectMail(HttpSession session, int page);

    /**
     * @Title customerSelectFaultMail
     * @Description: TODO //用户查看"收件/派件不成功"的寄件
     * @param session
     * @param page
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/11/17  17:37
     **/
    String customerSelectFaultMail(HttpSession session, int page);
}
