package com.rsc.service;

import com.rsc.entity.Customer;
import com.rsc.entity.Mail;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

public interface CustomerService {

    /**
     * @Title customerToRegister
     * @Description: TODO 用户注册
     * @param customer
     * @param model
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/11/16  1:09
     **/
    String customerToRegister(Customer customer, Model model);

    /**
     * @Title customerToLogin
     * @Description: TODO 验证登录
     * @param phone
     * @param password
     * @param session
     * @param model
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/11/16  0:54
     **/
    String customerToLogin(String phone, String password, HttpSession session, Model model);

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

    /**
     * @Title selectAssigningMail
     * @Description: TODO 返回收件状态是”收件完成“的且派件状态是“正在派件”或“派件异常”或“等待分配”或”准备派件“的单
     * @param session
     * @param page
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/12/2  17:52
     **/
    String selectAssigningMail(HttpSession session, int page);
}
