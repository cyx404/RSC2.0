package com.rsc.service;

import com.rsc.entity.Mail;
import com.rsc.entity.Postman;
import com.rsc.entity.Region;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

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
     * @param reason
     * @param page
     * @param model
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/11/17  18:00
     **/
    String postmanReceivefault(HttpSession session, Mail mailId, String reason, int page, Model model);

    /**
     *@Title: postmanToAssign
     *@Description: TODO  邮差查看需派的件
     *@Param: [session, page, i, s]
     *@return: java.lang.String
     *@Author: zsy
     *@date: 2019/11/21 10:56
     */
    String postmanToAssign(HttpSession session, int page, int i, String s);

    /**
     *@Title: postmanToAllAssign
     *@Description: TODO  邮差一键接需派送的单
     *@Param: [session]
     *@return: java.lang.String
     *@Author: zsy
     *@date: 2019/11/21 17:36
     */
    String postmanToAllAssign(HttpSession session);


    /**
     *@Title: postmanAssignfault
     *@Description: TODO  邮差确定某邮件派送故障
     *@Param: [session, mailId, season, page, model]
     *@return: java.lang.String
     *@Author: zsy
     *@date: 2019/11/21 18:14
     */
    String postmanAssignfault(HttpSession session, Mail mailId, String reason, int page, Model model);

    /**
     *@Title: postmanToDetermineAssigned
     *@Description: TODO 确认派送状态
     *@Param: [session, page, mailId, mailStateId, str]
     *@return: java.lang.String
     *@Author: zsy
     *@date: 2019/11/24 17:14
     */
    String postmanToDetermineAssigned(HttpSession session, int i, int mailStateId, String str);

    /**
     *@Title: postmanAssignedSuccess
     *@Description: TODO  派送成功
     *@Param: [session, i, s]
     *@return: java.lang.String
     *@Author: zsy
     *@date: 2019/11/24 21:27
     */
    String postmanAssignedSuccess(HttpSession session,int page, int mailStateId, String str);


    /**
     *@Title: postmanAssignException
     *@Description: TODO 派件异常订单界面
     *@Param: [session, i, i1, s]
     *@return: java.lang.String
     *@Author: zsy
     *@date: 2019/11/26 14:41
     */
    String postmanAssignException(HttpSession session, int page, int stateId, String str);

    /**
     *@Title: exceptionToSuccess
     *@Description: TODO 异常邮件派送成功
     *@Param: [session, mailId, page, model]
     *@return: java.lang.String
     *@Author: zsy
     *@date: 2019/11/26 14:56
     */
    String exceptionToSuccess(HttpSession session, Mail mailId, int page, Model model);
}
