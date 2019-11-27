package com.rsc.service;

import javax.servlet.http.HttpSession;

public interface ManagerService {
    String managerToLogin(String phone,String password, HttpSession session);
}
