package com.rsc.service;

import org.springframework.ui.Model;

public interface AdminService {

    /**
     * @Title distributeReceivingWorksToPostmans
     * @Description: TODO 分配收件工作给已经签到上班的快递员
     * @param model
     * @return String
     * @Author: chenyx
     * @Date: 2019/11/20  0:57
     **/
    String distributeReceivingWorksToPostmans(Model model);

    /**
     * @Title distributeAssigningWorksToPostmans
     * @Description: TODO 分配派件工作给已经签到上班的快递员
     * @param model
     * @return String
     * @Author: chenyx
     * @Date: 2019/11/20  0:57
     **/
    String distributeAssigningWorksToPostmans(Model model);


}
