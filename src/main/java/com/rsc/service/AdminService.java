package com.rsc.service;

import org.springframework.ui.Model;

public interface AdminService {

    /**
     * @Title assignWorksToPostmans
     * @Description: TODO 分配工作给已经签到上班的快递员
     * @param model
     * @return String
     * @Author: chenyx
     * @Date: 2019/11/20  0:57
     **/
    String assignWorksToPostmans(Model model);

}
