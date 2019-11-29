package com.rsc.service.Impl;

import com.rsc.entity.Manager;
import com.rsc.repository.ManagerRepository;
import com.rsc.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpSession;

@Service
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    ManagerRepository managerRepository;

    @Override
    public String managerToLogin(String phone, String password, HttpSession session) {
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        Manager manager = managerRepository.findManagerByMphoneAndMpassword(phone, md5Password);
        if (null == manager) {
            session.setAttribute("merror", "手机号或密码错误！");
            return "admin/adminLogin.html";
        } else {
            session.setAttribute("manager", manager);
            session.setAttribute("mname",manager.getMname());
            System.out.println("jjjjjjjjjjjjjjjjjjjj"+manager.getMname());
            return "admin/adminIndex.html";
        }
    }
}
