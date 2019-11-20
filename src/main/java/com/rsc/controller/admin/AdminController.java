package com.rsc.controller.admin;

import com.rsc.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ClassName:AdminController
 * @Description:TODO  管理员
 * @Author:chenyx
 * @Date:Create in  2019/11/20 0:54
 **/
@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("beginworking")
    public String beginWorking(Model model) {
        return adminService.assignWorksToPostmans(model);
    }
}
