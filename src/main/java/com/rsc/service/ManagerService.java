package com.rsc.service;

import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

public interface ManagerService {
    String managerToLogin(String phone, String password, Model model, HttpSession session);
}
