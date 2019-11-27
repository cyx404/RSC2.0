package com.rsc.controller.admin;

import com.rsc.service.AdminService;
import com.rsc.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @ClassName:AdminController
 * @Description:TODO 管理员
 * @Author:chenyx
 * @Date:Create in  2019/11/20 0:54
 **/
@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ManagerService managerService;

    //收件工作分配
    @GetMapping("beginreceiving")
    public String beginReceiving(Model model) {
        return adminService.distributeReceivingWorksToPostmans(model);
    }

    //派件工作分配
    @GetMapping("beginassigning")
    public String beginAssigning(Model model) {
        return adminService.distributeAssigningWorksToPostmans(model);
    }

    //xiaqi:管理页面
    @RequestMapping(value = "admin")
    public String admin(){return "admin/adminIndex.html";}

    //xiaqi:登录页面
    @PostMapping(value = "mtologin")
    public String managerToLogin(@RequestParam String phone, @RequestParam String password, HttpSession session) {
        return managerService.managerToLogin(phone, password, session);
    }

    @RequestMapping(value = "mLogin")
    public String mLogin(){
        return "admin/adminLogin.html";
    }
}
