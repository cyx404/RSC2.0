package com.rsc.controller.customer;

import com.rsc.entity.Customer;
import com.rsc.entity.Region;
import com.rsc.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @ClassName:IndexController
 * @Description:TODO 用户页面跳转
 * @Author:chenyx
 * @Date:Create in  2019/11/15 20:00
 **/
@Controller
public class IndexController1 {

    @Autowired
    private RegionRepository regionRepository;

    //返回用户使用的主页(登录/注册)
    @GetMapping("cindex")
    public String customerIndex(HttpSession session) {
            return "customer/index";
    }


    //返回用户使用的主页
    @GetMapping("cindex2")
    public String customerIndex2(HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (null == customer)
            return "customer/login";
        else
            return "customer/cindex";
    }

    //返回用户使用的注册页面
    @GetMapping("register")
    public String customerRegister(Model model) {
        List<Region> regions = regionRepository.findAll();
        model.addAttribute("regions", regions);
        return "customer/register";
    }

    //返回用户使用的登录页面
    @GetMapping("clogin")
    public String customerLogin() {
        return "customer/login";
    }

    //返回用户寄快递页面
    @GetMapping("cmail")
    public String customerToDelivery(Model model) {
        List<Region> regions = regionRepository.findAll();
        model.addAttribute("regions", regions);
        return "customer/cmail";
    }


}
