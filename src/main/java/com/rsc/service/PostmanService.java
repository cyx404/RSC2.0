package com.rsc.service;

import com.rsc.entity.Attendance;
import com.rsc.entity.Mail;
import com.rsc.entity.Postman;
import com.rsc.entity.Region;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface PostmanService {

    /**
     * @Title postmanToLogin
     * @Description: TODO 验证登录
     * @param phone
     * @param password
     * @param session
     * @param model
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/11/16  13:15
     **/
    String postmanToLogin(String phone, String password, HttpSession session,Model model);


    /**
     * @Title postmanAddWorkload
     * @Description: TODO 邮差在工作量表生成一条记录(代表这一天的工作)
     * @param session
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/11/16  14:21
     **/
    String postmanAddWorkload(HttpSession session);


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
    int updateWorkloadExpectationWorkloadByPostman(int year, int month, int date, Postman postman);

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
    String postmanToReceive(HttpSession session, int page, int mailStateId, String str);

    /**
     * @Title postmanToAllReceive
     * @Description: TODO 邮差一键全部接单(接全部收件)
     * @param session
     * @param response
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/11/17  12:45
     **/
    String postmanToAllReceive(HttpSession session,HttpServletResponse response);


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

    //xiaqi:查询所有邮差的工资情况
    List<List> findAllPostmanSalary(int year,int month);

    //查询某一邮差
    Postman findPostman(String name);

    //xiaqi：查找某一员工的考勤情况
    List<Attendance> findAttendencesByPid(int pid, HttpSession session);

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
    String postmanAssignedSuccess(HttpSession session, int page, int mailStateId, String str);


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

    /**
     * @Title historicalReceive
     * @Description: TODO 历史收件：返回收件状态是“收件成功”和“收件失败”的件
     * @param session
     * @param page
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/12/2  18:45
     **/
    String historicalReceive(HttpSession session, int page);


    /**
     * @Title historicalAssign
     * @Description: TODO  历史派件：返回派件状态是“派件签收”和“派件失败”的件
     * @param session
     * @param page
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/12/2  18:58
     **/
    String historicalAssign(HttpSession session, int page);


    /**
     * @Title downExcelOfReceiveMail
     * @Description: TODO 下载正在收件的件
     * @param response
     * @param session
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/12/19  0:15
     **/
    String downExcelOfReceiveMail(HttpSession session,HttpServletResponse response);


    /**
     * @Title downExcelOfAssigneMail
     * @Description: TODO 下载正在派件+派件异常的件
     * @param session
     * @param response
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/12/19  1:18
     **/
    String downExcelOfAssigneMail(HttpSession session, HttpServletResponse response);
}
