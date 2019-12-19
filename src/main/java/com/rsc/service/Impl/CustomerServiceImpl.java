package com.rsc.service.Impl;

import com.rsc.entity.Customer;
import com.rsc.entity.Mail;
import com.rsc.entity.MailState;
import com.rsc.entity.Postman;
import com.rsc.repository.CustomerRepository;
import com.rsc.repository.MailRepository;
import com.rsc.repository.MailStateRepository;
import com.rsc.repository.WorkloadRepository;
import com.rsc.service.CustomerService;
import com.rsc.service.PostmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @ClassName:CustomerServiceImpl
 * @Description:TODO
 * @Author:chenyx
 * @Date:Create in  2019/11/15 21:22
 **/

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MailRepository mailRepository;

    @Autowired
    private MailStateRepository mailStateRepository;

    @Autowired
    private HttpSession session;

    @Autowired
    private PostmanService postmanService;

    @Autowired
    private WorkloadRepository workloadRepository;

    /**
     * @Title customerToRegister
     * @Description: TODO 用户注册信息
     * @param customer
     * @param model
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/11/16  1:09
     **/
    @Transactional
    @Override
    public String customerToRegister(Customer customer, Model model) {
        Customer customer1 = customerRepository.findCustomerByPhone(customer.getPhone());
        if (null != customer1) {
            model.addAttribute("cerror", "手机号已经存在！");
            return "forward:/rsc/customer/register";
        } else {
            String md5Password = DigestUtils.md5DigestAsHex(customer.getPassword().getBytes());
            customer.setPassword(md5Password);
            customerRepository.save(customer);
            model.addAttribute("success", "注册成功！");
            //return "customer/success";
            return "forward:/rsc/customer/clogin";
        }
    }


    /**
     * @Title customerToLogin
     * @Description: TODO 验证登录
     * @param phone
     * @param password
     * @param session
     * @param model
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/11/29  18:28
     **/
    @Override
    public String customerToLogin(String phone, String password, HttpSession session, Model model) {
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        Customer customer = customerRepository.findCustomerByPhoneAndPassword(phone, md5Password);
        if (null == customer) {
            model.addAttribute("cerror", "手机号或密码错误！");
            return "customer/login";
        } else {
            session.setAttribute("customer", customer);
            return "customer/index";
        }
    }


    /**
     * @Title customerToDelivery
     * @Description: TODO 客户寄快递
     * @param mail
     * @param session
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/11/16  22:02
     **/
    @Transactional
    @Override
    public String customerToDelivery(Mail mail, HttpSession session) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int date = cal.get(Calendar.DATE);

        Customer customer = (Customer) session.getAttribute("customer");
        if (null != customer) {
            try {//快递小哥上班了，立即分配任务
                Postman postman = postmanService.selectPostmantoWork(year, month, date, mail.getReceiveRegion());////分配收件员
                mail.setDistributeReceiveTime(new Date());//设置系统分配时间
                mail.setReceivePostman(postman);//收件员
                MailState mailStateReadying = mailStateRepository.findMailStateById(1);//返回“准备收件”状态
                mail.setReceiveState(mailStateReadying);//收件状态
                System.out.println("分配到的收件员:" + postman.getName());
                //该收件员的预期总工作量+1
                int a = postmanService.updateWorkloadExpectationWorkloadByPostman(year, month, date, postman);
                System.out.println("分配到的收件员的预期总工作量+1影响到的行数:" + a);

            } catch (Exception e) {//快递小哥还没上班，收件员为空但订单先存起来，等明天上班，管理员再分配
                MailState mailStateNull = mailStateRepository.findMailStateById(0);//返回“等待分配”状态
                mail.setReceiveState(mailStateNull);//收件状态
                System.out.println("快递小哥还没上班，收件员为空但订单先存起来，等明天上班，管理员再分配");
            }

            mail.setCustomer(customer);//所属客户
            //mail.setReceiveRegion(customer.getRegion());//上门收件地区

//            MailState mailStateNull = mailStateRepository.findMailStateById(0);//返回空件状态
//            mail.setAssignState(mailStateNull);//派件状态
            mail.setCreateTime(new Date());//订单生成时间
            mailRepository.save(mail);
            session.setAttribute("success", "提交成功！请耐心等候快递小哥上门收件！");
            return "customer/success";

        } else {
            System.out.println("客户session过时了！");
            return "customer/login";
        }
    }

    /**
     * @Title customerCheckOrDetermine
     * @Description: TODO 用户查看/确定邮件是否已经收取/付款：返回”准备收件“状态+“正在收件”+”等待分配“状态的单
     * @param session
     * @param page
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/11/17  14:08
     **/
    @Override
    public String customerCheckOrDetermine(HttpSession session, int page) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (null == customer)
            return "customer/login";
        else {
            Pageable pageable = PageRequest.of(page, 6);//分页，每页多少条记录
            MailState mailStateReadying = mailStateRepository.findMailStateById(1);//返回准备收件状态
            MailState mailStateReceiving = mailStateRepository.findMailStateById(2);//返回正在收件状态
            MailState mailStateWaitingDistribution = mailStateRepository.findMailStateById(0);//返回等待分配状态
            Page<Mail> mailPage = mailRepository.findMailByCustomerAndReadyingOrReceiving(customer, mailStateReadying, mailStateReceiving, mailStateWaitingDistribution, pageable);
            int totalPages = mailPage.getTotalPages();//一共多少页
            if (0 == totalPages) {//0页
                session.setAttribute("success", "您没有正在处理的单！");
                return "customer/success";
            } else {
                List<Mail> mailList = mailPage.getContent();
                int count = mailList.size();
                session.setAttribute("page", page);
                session.setAttribute("TotalPages", totalPages);
                session.setAttribute("mailList", mailList);
                session.setAttribute("count", count);
                return "customer/checkOrDetermine";
            }
        }

    }

    /**
     * @Title customerDetermineMail
     * @Description: TODO 用户确定邮件已经被邮差收取,邮差收件工作量+1，总工作量+1
     * @param session
     * @param mailId
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/11/17  15:22
     **/
    @Transactional
    public String customerDetermineMail(HttpSession session, int mailId) {

        MailState mailStateFinishing = mailStateRepository.findMailStateById(3);//返回收件完成状态
        MailState mailStateReceiving = mailStateRepository.findMailStateById(2);//返回正在收件状态
        MailState mailStateWaiting = mailStateRepository.findMailStateById(0);//返回等待分配状态
        //邮件收件状态为“正在收件”的单更新为邮件状态“收件完成”，同时派件状态修改为“等待分配”
        int num = mailRepository.updateAMailReceiveStateToFinishingAndAssignStateToWaiting(mailStateFinishing, new Date(), mailStateReceiving, mailStateWaiting, mailId);
        System.out.println("num:" + num);
        if (1 == num) {
            session.setAttribute("success", "付款成功！");
//            int mailId1 = mailId.getId();
            //完成状态，即该单对应邮差工作量（收件计件数加1，工作总量+1）
            Mail mail = mailRepository.findMailById(mailId);
            Calendar cal = Calendar.getInstance();
            cal.setTime(mail.getDistributeReceiveTime());//获取当初系统分配收件工作的时间
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;
            int date = cal.get(Calendar.DATE);
            System.out.println(year + " " + month + " " + date);
            //完成状态，即该单对应邮差工作量（收件计件数加1，工作总量+1）
            int a = workloadRepository.updateWorkloadReceiveWorkloadAndTotalWorkloadByPostmanAndYearAndMonthAndDate(year, month, date, mail.getReceivePostman());
            System.out.println("收件计件数加1，工作总量+1：" + a);

            try {//快递小哥上班了，立即找人来分配派件任务
                MailState mailStateReadyingAssign = mailStateRepository.findMailStateById(5);//返回“准备派件”状态
                Postman postman = postmanService.selectPostmantoWork(year, month, date, mail.getAssignRegion());////分配派件员
                mailRepository.addAMailAssignPostman(postman, mailStateReadyingAssign, new Date(), mail); //根据寄件给它分配派件员,派件状态变成”准备派件“,设置系统分配派件时间
                workloadRepository.updateWorkloadExpectationWorkloadByPostmanAndYearAndMonthAndDate(year, month, date, postman);//派件员预期工作总量+1
            } catch (Exception e) {//快递小哥还没上班，收件员为空但订单先存起来，等明天上班，管理员再分配
                System.out.println("分配派件工作失败没人上班，等待管理员分配");
            }



            return "customer/success";
        } else {
            session.setAttribute("success", "付款失败！快递小哥还没揽件！");
            return "customer/success";
        }

    }

    /**
     * @Title selectAssigningMail
     * @Description: TODO 返回收件状态是”收件完成“的且派件状态是“正在派件”或“派件异常”或“等待分配”或”准备派件“的单
     * @param session
     * @param page
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/12/2  17:54
     **/
    @Override
    public String selectAssigningMail(HttpSession session, int page) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (null == customer)
            return "customer/login";
        else {
            Pageable pageable = PageRequest.of(page, 6);//分页，每页多少条记录
            MailState mailStateFinishing = mailStateRepository.findMailStateById(3);//返回完成收件状态
            MailState mailStateE = mailStateRepository.findMailStateById(8);//返回派件异常状态
            MailState mailStateF = mailStateRepository.findMailStateById(7);//返回派件签收状态
            Page<Mail> mailPage = mailRepository.selectAssigningMail(customer, mailStateFinishing, mailStateE, mailStateF, pageable);
            int totalPages = mailPage.getTotalPages();//一共多少页
            if (0 == totalPages) {//0页
                session.setAttribute("success", "您没有该状态的邮件！");
                return "customer/success";
            } else {
                List<Mail> mailList = mailPage.getContent();
                session.setAttribute("page", page);
                session.setAttribute("count", mailList.size());
                session.setAttribute("TotalPages", totalPages);
                session.setAttribute("mailList", mailList);
                return "customer/selectAssigningMail";
            }
        }

    }

    /**
     * @Title customerSelectMail
     * @Description: TODO 返回收件状态为”收件完成“且派件状态为“派件签收”的单
     * @param session
     * @param page
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/11/17  17:17
     **/
    @Override
    public String customerSelectMail(HttpSession session, int page) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (null == customer)
            return "customer/login";
        else {
            Pageable pageable = PageRequest.of(page, 6);//分页，每页多少条记录
            MailState mailStateFinishing = mailStateRepository.findMailStateById(3);//返回完成收件状态
            MailState mailStateAS = mailStateRepository.findMailStateById(7);//返回派件签收状态
            Page<Mail> mailPage = mailRepository.findMailByCustomerAndFinishing(customer, mailStateFinishing, mailStateAS, pageable);
            int totalPages = mailPage.getTotalPages();//一共多少页
            if (0 == totalPages) {//0页
                session.setAttribute("success", "您没有该状态的邮件！");
                return "customer/success";
            } else {
                List<Mail> mailList = mailPage.getContent();
                session.setAttribute("count", mailList.size());
                session.setAttribute("page", page);
                session.setAttribute("TotalPages", totalPages);
                session.setAttribute("mailList", mailList);
                return "customer/selectmail";
            }
        }
    }

    /**
     * @Title customerSelectFaultMail
     * @Description: TODO //用户查看"收件/派件不成功"的寄件：返回”收件/派件不成功“状态的单
     * @param session
     * @param page
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/11/17  17:37
     **/
    @Override
    public String customerSelectFaultMail(HttpSession session, int page) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (null == customer)
            return "customer/login";
        else {
            Pageable pageable = PageRequest.of(page, 8);//分页，每页多少条记录
            MailState mailStateReceiveFault = mailStateRepository.findMailStateById(4);//返回“收件不成功"状态
            MailState mailStateAssignFault = mailStateRepository.findMailStateById(8);//返回”派件不成功“状态
            Page<Mail> mailPage = mailRepository.findMailByCustomerAndFault(customer, mailStateReceiveFault, mailStateAssignFault, pageable);
            int totalPages = mailPage.getTotalPages();//一共多少页
            if (0 == totalPages) {//0页
                session.setAttribute("success", "您没有失败订单！");
                return "customer/success";
            } else {
                List<Mail> mailList = mailPage.getContent();
                session.setAttribute("page", page);
                session.setAttribute("TotalPages", totalPages);
                session.setAttribute("mailList", mailList);
                session.setAttribute("count", mailList.size());
                return "customer/selectfaultmail";
            }
        }
    }
}
