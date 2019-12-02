package com.rsc.controller.customer;

import com.rsc.entity.Customer;
import com.rsc.entity.Region;
import com.rsc.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @ClassName:IndexController
 * @Description:TODO 用户页面跳转
 * @Author:chenyx
 * @Date:Create in  2019/11/15 20:00
 **/
@RequestMapping("rsc/customer")
@Controller
public class IndexController1 {

    @Autowired
    private RegionRepository regionRepository;

    @RequestMapping("welcome")
    public String welcome() {
        return "customer/welcome";
    }

    //返回用户使用的主页(登录/注册)
    @RequestMapping("clogin")
    public String customerIndex(HttpSession session) {
            return "customer/login";
    }


    //返回用户使用的注册页面
    @RequestMapping("register")
    public String customerRegister(Model model) {
        List<Region> regions = regionRepository.findAll();
        model.addAttribute("regions", regions);
        return "customer/register";
    }


    //返回用户寄快递页面
    @GetMapping("cmail")
    public String customerToDelivery(Model model) {
        List<Region> regions = regionRepository.findAll();
        model.addAttribute("regions", regions);
        return "customer/cmail";
    }


    @RequestMapping("clogout")
    public String clogout(HttpSession session){
        System.out.println("clogout");
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            System.out.println("空工号，没登录！");
            return "customer/login";
        } else {
            session.removeAttribute("customer");
        }
        return "customer/login";
    }


}
