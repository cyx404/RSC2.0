package com.rsc.service;

import com.rsc.entity.Attendance;
import com.rsc.entity.Postman;
import com.rsc.entity.Region;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface PostmanService {

    /**
     * @Title postmanToLogin
     * @Description: TODO 验证登录
     * @param phone
     * @param password
     * @param session
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/11/16  13:15
     **/
    String postmanToLogin(String phone, String password, HttpSession session);


    /**
     * @Title postmanAddWorkload
     * @Description: TODO 邮差在工作量表生成一条记录(代表这一天的工作)
     * @param session
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/11/16  14:21
     **/
    String postmanAddWorkload( HttpSession session);


    /**
     * @Title updateWorkloadExpectationWorkloadByPostman
     * @Description: TODO 邮差的预期总工作量+1
     * @param year
     * @param month
     * @param date
     * @param postman
     * @return int
     * @Author: chenyx
     * @Date: 2019/11/17  16:44
     **/
     int  updateWorkloadExpectationWorkloadByPostman(int year, int month, int date,Postman postman);

    /**
     * @Title selectPostmantoWork
     * @Description: TODO  挑选出一个快递员（属于他负责地区）去收快递，思路是按那一天的预期总工作量从升序排列，找去那个最少工作量的快递员去收件
     * @param year
     * @param month
     * @param date
     * @param region
     * @return com.rsc.entity.Postman
     * @Author: chenyx
     * @Date: 2019/11/16  22:31
     **/
    Postman selectPostmantoWork(int year, int month, int date, Region region) throws IndexOutOfBoundsException;



    /**
     * @Title postmanToReceive
     * @Description: TODO  邮差去收快递 或 邮差确定邮件是否收取成功
     * @param session
     * @param page
     * @param mailStateId
     * @param str  return str
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/11/17  13:19
     **/
    String postmanToReceive(HttpSession session,int page,int mailStateId,String str);

    /**
     * @Title postmanToAllReceive
     * @Description: TODO 邮差一键全部接单(接全部收件)
     * @param session
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/11/17  12:45
     **/
    String postmanToAllReceive(HttpSession session);


    /**
     * @Title postmanReceivefault
     * @Description: TODO 邮差确定某邮件收件故障
     * @param session
     * @param mailId
     * @param season
     * @param page
     * @param model
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/11/17  18:00
     **/
    String postmanReceivefault(HttpSession session, int mailId, String season, int page, Model model);

    //查询所有邮差的工资情况
     List<List> findAllPostmanSalary();

     //查询某一邮差
    Postman findPostman(String name);

    //xiaqi：查找某一员工的考勤情况
    List<Attendance> findAttendencesByPid(int pid,HttpSession session);
}
