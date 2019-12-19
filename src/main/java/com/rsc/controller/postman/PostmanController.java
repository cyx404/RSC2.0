package com.rsc.controller.postman;

import com.rsc.entity.Mail;
import com.rsc.service.PostmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @ClassName:CustomerController
 * @Description:TODO
 * @Author:chenyx
 * @Date:Create in  2019/11/15 19:59
 **/
@RequestMapping("rsc/pm")
@Controller
public class PostmanController {

    @Autowired
    private PostmanService postmanService;

    //邮差提交的登录
    @PostMapping("ptologin")
    public String postmanToLogin(@RequestParam String phone, @RequestParam String password, HttpSession session, Model model) {
        return postmanService.postmanToLogin(phone, password, session, model);
    }

    //邮差去签到
    @GetMapping("pduty")
    public String postmanToDuty(HttpSession session) {
        return postmanService.postmanAddWorkload(session);
    }

    //邮差查看要取的邮件
    @GetMapping("preceive")
    public String postmanToReceive(HttpSession session, @RequestParam int page) {
        return postmanService.postmanToReceive(session, page, 1, "postman/receive");
    }

    //邮差一键全部接单(接全部收件)
    @GetMapping("ptoallreceive")
    public String postmanToAllReceive(HttpSession session, HttpServletResponse response) {
        return postmanService.postmanToAllReceive(session, response);
    }

    //邮差确定邮件是否收取成功
    @RequestMapping("pdeterminereceive")
    public String postmanDetermineReceive(HttpSession session, @RequestParam int page) {
        return postmanService.postmanToReceive(session, page, 2, "postman/determineReceive");//可以公用
    }

    //邮差确定某邮件收件故障
    @RequestMapping("receivefault")
    public String postmanReceivefault(HttpSession session, @RequestParam Mail mailId, @RequestParam String reason, @RequestParam int page, Model model) {
        return postmanService.postmanReceivefault(session, mailId, reason, page, model);
    }

    //邮差查看需派的件
    @GetMapping("passign")
    public String postmanToAssign(HttpSession session, @RequestParam int page) {
        return postmanService.postmanToAssign(session, page, 5, "postman/assign");
    }

    //邮差确定邮件派件状态
    @RequestMapping("pdetermineassigned")
    public String postmanDetermineAssign(HttpSession session, @RequestParam int page) {
        return postmanService.postmanToDetermineAssigned(session, page, 6, "postman/determineAssign");
    }

    //确认派送成功
    @RequestMapping("passignsuccess")
    public String postmanAssignSuccess(HttpSession session) {
        return postmanService.postmanAssignedSuccess(session, 0, 6, "postman/assignSuccess");
    }

    //邮差一键全部接单(接全部派件)
    @GetMapping("ptoallassign")
    public String postmanToAllAssign(HttpSession session) {
        return postmanService.postmanToAllAssign(session);
    }

    //邮差确定某邮件派件故障
    @RequestMapping("assignfault")
    public String postmanAssignfault(HttpSession session, @RequestParam Mail mailId, @RequestParam String reason, @RequestParam int page, Model model) {
        return postmanService.postmanAssignfault(session, mailId, reason, page, model);
    }

    //获取派件异常的订单界面
    @RequestMapping("assignException")
    public String postmanAssignException(HttpSession session, @RequestParam int page) {
        return postmanService.postmanAssignException(session, page, 9, "postman/assignException");
    }

    //异常邮件派送成功
    @RequestMapping("exceptionToSuccess")
    public String exceptionToSuccess(HttpSession session, @RequestParam Mail mailId, @RequestParam int page, Model model) {
        return postmanService.exceptionToSuccess(session, mailId, page, model);
    }

    //历史收件：返回收件状态是“收件成功”和“收件失败”的件
    @RequestMapping("historicalreceive")
    public String historicalReceive(HttpSession session, @RequestParam int page) {
        return postmanService.historicalReceive(session, page);
    }

    //历史派件：返回派件状态是“派件签收”和“派件失败”的件
    @RequestMapping("historicalassign")
    public String historicalAssign(HttpSession session, @RequestParam int page) {
        return postmanService.historicalAssign(session, page);
    }

    //下载正在收件的件
    @ResponseBody
    @RequestMapping(value = "downExcelOfReceiveMail", produces = {"application/vnd.ms-excel;charset=UTF-8"})
    public String downExcelOfReceiveMail(HttpSession session, HttpServletResponse response){
      return postmanService.downExcelOfReceiveMail(session,response);
    }

    //下载正在派件+派件异常的件
    @ResponseBody
    @RequestMapping(value = "downExcelOfAssigneMail", produces = {"application/vnd.ms-excel;charset=UTF-8"})
    public String downExcelOfAssigneMail(HttpSession session, HttpServletResponse response){
        return postmanService.downExcelOfAssigneMail(session,response);
    }


}
