package com.rsc.service.Impl;

import com.rsc.entity.*;
import com.rsc.repository.*;
import com.rsc.service.PostmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @ClassName:PostmanServiceImpl
 * @Description:TODO
 * @Author:chenyx
 * @Date:Create in  2019/11/16 13:15
 **/

@Service
public class PostmanServiceImpl implements PostmanService {

    @Autowired
    private PostmanRepository postmanRepository;

    @Autowired
    private WorkloadRepository workloadRepository;

    @Autowired
    private MailStateRepository mailStateRepository;

    @Autowired
    private MailRepository mailRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    /**
     * @Title postmanToLogin
     * @Description: TODO 验证登录
     * @param phone
     * @param password
     * @param session
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/11/16  13:16
     **/
    @Override
    public String postmanToLogin(String phone, String password, HttpSession session) {
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        Postman postman = postmanRepository.findPostmanByPhoneAndPassword(phone, md5Password);
        if (null == postman) {
            session.setAttribute("perror", "手机号或密码错误！");
            return "postman/login";
        } else {
            session.setAttribute("postman", postman);
            return "postman/index";
        }
    }

    /**
     * @Title postmanAddWorkload
     * @Description: TODO 邮差在工作量表生成一条记录(代表这一天的工作)
     * @param session
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/11/16  14:22
     **/
    @Transactional
    @Override
    public String postmanAddWorkload(HttpSession session) {
        Postman postman = (Postman) session.getAttribute("postman");
        if (postman == null) {
            System.out.println("==========空工号，没登录！==========");
        } else {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;
            int date = cal.get(Calendar.DATE);
            Workload workload = workloadRepository.findWorkloadByPostmanAndYearAndMonthAndDate(postman, year, month, date);

            //判断当天是否已经签到
            if (null != workload) {//当天已经签到了
                System.out.println(workload.getId());
                session.setAttribute("psuccess", "你今天已经签到了！");
                return "postman/success";
            } else {//还没签到，生成一条记录(代表这一天的工作)，考勤表当月+1
                Workload workload2 = new Workload(postman, year, month, date);
                workloadRepository.save(workload2);

                //考勤表当月+1
                Attendance attendance = attendanceRepository.findAttendanceByPostmanAndYearAndMonth(postman, year, month);
                if (null == attendance) {//当月还没有考勤记录，则增加当月的考勤记录
                    Attendance newattendance = new Attendance();
                    newattendance.setPostman(postman);
                    newattendance.setYear(year);
                    newattendance.setMonth(month);
                    newattendance.setDuty(1);//当天签到上班
                    attendanceRepository.save(newattendance);
                } else {//当月有考勤记录，直接当月考勤+1
                    int a = attendanceRepository.updateAttendanceDutyByPostmanAndYearAndMonth(postman, year, month);
                    System.out.println("签到影响行数：" + a);
                }
                session.setAttribute("psuccess", "签到成功！");
            }
        }
        return "postman/success";
    }


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
    @Transactional
    @Override
    public int updateWorkloadExpectationWorkloadByPostman(int year, int month, int date, Postman postman) {
        return workloadRepository.updateWorkloadExpectationWorkloadByPostmanAndYearAndMonthAndDate(year, month, date, postman);
    }

    /**
     * @Title selectPostmantoWork
     * @Description: TODO 挑选出一个快递员（属于他负责地区）去收快递，思路是按那一天的预期总工作量从升序排列，找去那个最少工作量的快递员去收件
     * @param year
     * @param month
     * @param date
     * @param region
     * @return com.rsc.entity.Postman
     * @Author: chenyx
     * @Date: 2019/11/16  22:33
     **/
    @Override
    public Postman selectPostmantoWork(int year, int month, int date, Region region) throws IndexOutOfBoundsException {
        System.out.println("客户的地区：" + region.getId());
        Pageable pageable = PageRequest.of(0, 1, Sort.Direction.ASC, "expectationWorkload");//升序排列
        Page<Workload> workloadPage = workloadRepository.findFirstWorkloadtoDelivery(year, month, date, region, pageable);////分配收件员
        List<Workload> workloads = workloadPage.getContent();
        return workloads.get(0).getPostman();
    }


    /**
     * @Title postmanToReceive
     * @Description: TODO 邮差去收快递 或 邮差确定邮件是否收取成功
     * @param session
     * @param page
     * @param mailStateId  邮件状态id
     * @param str  return str
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/11/17  13:18
     **/
    @Transactional
    @Override
    public String postmanToReceive(HttpSession session, int page, int mailStateId, String str) {
        Postman postman = (Postman) session.getAttribute("postman");
        if (postman == null) {
            System.out.println("==========空工号，没登录！==========");
            return "postman/login";
        } else {
            Pageable pageable = PageRequest.of(page, 10);//分页，每页多少条记录
            MailState mailState = mailStateRepository.findMailStateById(mailStateId);//返回准备派件状态
            Page<Mail> mailPage = mailRepository.findMailByReceivePostmanAndReceiveState(postman, mailState, pageable);
            int totalPages = mailPage.getTotalPages();//一共多少页
            if (0 == totalPages) {//0页
                session.setAttribute("psuccess", "你没有单要处理！");
                return "postman/success";
            } else {
                List<Mail> mailList = mailPage.getContent();
                session.setAttribute("page", page);
                session.setAttribute("TotalPages", totalPages);
                session.setAttribute("mailList", mailList);
                //return "postman/receive";
                return str;
            }
        }

    }

    /**
     * @Title postmanToAllReceive
     * @Description: TODO 邮差一键全部接单(接全部收件)
     * @param session
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/11/17  12:45
     **/
    @Transactional
    @Override
    public String postmanToAllReceive(HttpSession session) {
        Postman postman = (Postman) session.getAttribute("postman");
        if (postman == null) {
            System.out.println("==========空工号，没登录！==========");
            return "postman/login";
        } else {
            MailState mailStateReadying = mailStateRepository.findMailStateById(1);//返回准备收件状态
            MailState mailStateReceiving = mailStateRepository.findMailStateById(2);//返回正在收件状态
            int num = mailRepository.updateReceiveStateToReceiving(mailStateReceiving, mailStateReadying, postman);
            session.setAttribute("psuccess", "一共 " + num + " 单，收件接单成功！");
            return "postman/success";
        }
    }


    @Override
    public String postmanReceivefault(HttpSession session, int mailId, String season, int page, Model model) {
        return null;
    }


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
    @Transactional
    public String postmanReceivefault(HttpSession session, Mail mailId, String reason, int page, Model model) {
        Postman postman = (Postman) session.getAttribute("postman");
        if (postman == null) {
            System.out.println("==========空工号，没登录！==========");
            return "postman/login";
        } else {
            //设置收件状态为”收件不成功“，receiveFrequency（收件故障数）=1，故障原因
            MailState mailStateReceiveFault = mailStateRepository.findMailStateById(4);//返回收件不成功状态
            mailRepository.updateAMailReceiveFault(mailStateReceiveFault, reason, mailId.getId());

            //修改该邮差的当天的工作量：收件故障量+1，总故障量+1
            Calendar cal = Calendar.getInstance();
            cal.setTime(mailId.getCreateTime());
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;
            int date = cal.get(Calendar.DATE);
            System.out.println(year + " " + month + " " + date);
            workloadRepository.updateWorkloadReceiveFaultAndTotalFaultdByPostmanAndYearAndMonthAndDate(year, month, date, postman);

            model.addAttribute("psuccess", "单号:" + mailId.getId() + "--收件故障处理成功！");
            session.setAttribute("page", page);
            return "forward:/pdeterminereceive";
        }
    }

    @Override
    public List<List> findAllPostmanSalary() {
        List<Postman> postmanList = postmanRepository.findAllPostman();
        List<List> salaryList = new ArrayList<>();
        for (Postman p:postmanList) {
            List salary = new ArrayList();
            double total=3000;
            List<Workload> workloads = p.getWorkloads();
            for (Workload w:workloads) {
                total += w.getTotalWorkload()*5;
            }
            salary.add(p.getId());
            salary.add(p.getName());
            salary.add(total);
            salaryList.add(salary);
        }
        System.out.println(salaryList.toString());
        return salaryList;
    }

    @Override
    public Postman findPostman(String name) {
        return postmanRepository.findPostmanByName(name);
    }

    @Override
    public List<Attendance> findAttendencesByPid(int pid, HttpSession session) {
        List<Attendance> attendanceList = attendanceRepository.findAttendancesByPostman(pid);
        session.setAttribute("attendences",attendanceList);
        return null;
    }

    /**
     *@Title: postmanToAssign
     *@Description: TODO  快递员查看派件信息
     *@Param: [session, page, i, s]
     *@return: java.lang.String
     *@Author: zsy
     *@date: 2019/11/21 10:59
     */
    @Transactional
    public String postmanToAssign(HttpSession session, int page, int mailStateId, String str) {
        Postman postman = (Postman) session.getAttribute("postman");
        if (postman == null) {
            System.out.println("==========空工号，没登录！==========");
            return "postman/login";
        } else {
            Pageable pageable = PageRequest.of(page, 10);//分页，每页多少条记录
            MailState mailState = mailStateRepository.findMailStateById(mailStateId);//返回准备派件状态
            Page<Mail> mailPage = mailRepository.findMailByAssignPostmanAndAssignStateAndAssignFrequency(postman, mailState,1, pageable);
            int totalPages = mailPage.getTotalPages();//一共多少页
            if (0 == totalPages) {//0页
                session.setAttribute("psuccess", "你没有单要处理！");
                return "postman/success";
            } else {
                List<Mail> mailList = mailPage.getContent();
                session.setAttribute("page", page);
                session.setAttribute("TotalPages", totalPages);
                session.setAttribute("mailList", mailList);
                return str;
            }
        }
    }

    /**
     *@Title: postmanToDetermineAssigned
     *@Description: TODO  邮差确定是否为派件成功状态
     *@Param: [session, page, mailStateId, str]
     *@return: java.lang.String
     *@Author: zsy
     *@date: 2019/11/24 17:00
     */
    @Transactional
    public String postmanToDetermineAssigned(HttpSession session, int page, int mailStateId, String str) {
        Postman postman = (Postman) session.getAttribute("postman");
        if (postman == null) {
            System.out.println("==========空工号，没登录！==========");
            return "postman/login";
        } else {
            Pageable pageable = PageRequest.of(page, 10);//分页，每页多少条记录
            MailState mailState = mailStateRepository.findMailStateById(mailStateId);//返回正在派件状态
            Page<Mail> mailPage = mailRepository.findMailByAssignPostmanAndAssignStateAndAssignFrequency(postman, mailState,1, pageable);//获取派件次数为1、状态为正在派件的订单
            int totalPages = mailPage.getTotalPages();//一共多少页
            if (0 == totalPages) {//0页
                session.setAttribute("psuccess", "你没有单要处理！");
                return "postman/success";
            } else {
                List<Mail> mailList = mailPage.getContent();
                session.setAttribute("page", page);
                session.setAttribute("TotalPages", totalPages);
                session.setAttribute("mailList", mailList);
                return str;
            }
        }
    }

    /**
     *@Title: postmanAssignedSuccess
     *@Description: TODO  派送成功
     *@Param: [session, i, s]
     *@return: java.lang.String
     *@Author: zsy
     *@date: 2019/11/24 21:28
     */
    @Transactional
    public String postmanAssignedSuccess(HttpSession session,int page, int mailStateId, String str) {
        Postman postman = (Postman) session.getAttribute("postman");
//        System.out.println(postman.getName());
        Pageable pageable = PageRequest.of(page, 10);
//        System.out.println("1:"+pageable);
        MailState mailState = mailStateRepository.findMailStateById(mailStateId);//返回正在派件状态
        Page<Mail> mailPage = mailRepository.findMailByAssignPostmanAndAssignState(postman, mailState, pageable);
//        System.out.println("2:"+mailPage);
        List<Mail> mailList = mailPage.getContent();
        System.out.println(mailList);
        for (Mail mail : mailList) {
            int mailId = mail.getId();
            System.out.println(mailId+"\n"+mail.getAssignPostman());
            mailRepository.updateAMailAssignDetermineStateAndTime(mailId, new Date());//将正在派件状态改为派件成功状态并返回时间
            Calendar cal = Calendar.getInstance();
            cal.setTime(mail.getDistributeAssignTime());//获取当初系统分配派件工作的时间
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;
            int date = cal.get(Calendar.DATE);
            System.out.println(year + " " + month + " " + date);
            //派件工作量+1   总工作量+1
            int num = workloadRepository.updateWorkloadAssignWorkloadAndTotalWorkloadByPostmanAndYearAndMonthAndDate(year, month, date, mail.getReceivePostman());
            session.setAttribute("psuccess", "一共 " + num + " 单，派件成功！");
        }
        return str;
    }

    /**
     *@Title: postmanAssignException
     *@Description: TODO 派件异常订单界面
     *@Param: [session, i, i1, s]
     *@return: java.lang.String
     *@Author: zsy
     *@date: 2019/11/26 14:41
     */
    public String postmanAssignException(HttpSession session, int page, int stateId, String str) {
        Postman postman = (Postman) session.getAttribute("postman");
        if (postman == null) {
            System.out.println("==========空工号，没登录！==========");
            return "postman/login";
        } else {
            Pageable pageable = PageRequest.of(page, 10);//分页，每页多少条记录
            MailState mailState = mailStateRepository.findMailStateById(stateId);//返回派件异常状态
            Page<Mail> mailPage = mailRepository.findMailByAssignPostmanAndAssignStateAndAssignFrequencyBetween(postman, mailState,2,4,pageable);//获取派件次数>1、状态为派件异常的订单
            int totalPages = mailPage.getTotalPages();//一共多少页
            if (0 == totalPages) {//0页
                session.setAttribute("psuccess", "你没有单要处理！");
                return "postman/success";
            } else {
                List<Mail> mailList = mailPage.getContent();
                session.setAttribute("page", page);
                session.setAttribute("TotalPages", totalPages);
                session.setAttribute("mailList", mailList);
                return str;
            }
        }
    }

    /**
     *@Title: exceptionToSuccess
     *@Description: TODO 异常邮件成功派送
     *@Param: [session, mailId, page, model]
     *@return: java.lang.String
     *@Author: zsy
     *@date: 2019/11/26 14:56
     */
    @Transactional
    public String exceptionToSuccess(HttpSession session, Mail mailId, int page, Model model) {
        Postman postman = (Postman) session.getAttribute("postman");
        if (postman == null) {
            System.out.println("==========空工号，没登录！==========");
            return "postman/login";
        } else {
            Calendar cal = Calendar.getInstance();
            System.out.println(mailId.getDistributeAssignTime());
            cal.setTime(mailId.getDistributeAssignTime());
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;
            int date = cal.get(Calendar.DATE);
            System.out.println(year + " " + month + " " + date);
            mailRepository.updateAMailAssignSuccess(mailId.getId());//设置邮件为派件成功
            workloadRepository.updateWorkloadAssignWorkloadAndTotalWorkloadByPostmanAndYearAndMonthAndDate(year, month, date, postman);//派件工作量+1  总工作量+1
            model.addAttribute("psuccess", "单号:" + mailId.getId() + "--派件故障处理成功！");
            session.setAttribute("page", page);
            return "postman/assignSuccess";
        }
    }

    /**
     *@Title: postmanToAllAssign
     *@Description: TODO  一键接收需派送的单
     *@Param: [session]
     *@return: java.lang.String
     *@Author: zsy
     *@date: 2019/11/21 18:16
     */
    @Transactional
    public String postmanToAllAssign(HttpSession session) {
        Postman postman = (Postman) session.getAttribute("postman");
        if (postman == null) {
            System.out.println("==========空工号，没登录！==========");
            return "postman/login";
        } else {
            MailState mailStateReadying = mailStateRepository.findMailStateById(5);//返回准备派件状态
            MailState mailStateAssigning = mailStateRepository.findMailStateById(6);//返回正在派件状态
            int num = mailRepository.updateAssignStateToAssigning(mailStateAssigning, mailStateReadying, postman);
            session.setAttribute("psuccess", "一共 " + num + " 单，派件接单成功！");
            return "postman/success";
        }
    }

    /**
     *@Title: postmanAssignfault
     *@Description: TODO  邮差确定某邮件派送故障
     *@Param: [session, mailId, season, page, model]
     *@return: java.lang.String
     *@Author: zsy
     *@date: 2019/11/21 18:17
     */
    @Transactional
    public String postmanAssignfault(HttpSession session, Mail mailId, String reason, int page, Model model){
        String str;
        Postman postman = (Postman) session.getAttribute("postman");
        if (postman == null) {
            System.out.println("==========空工号，没登录！==========");
            return "postman/login";
        } else {
            //设置派件状态为”派件不成功“，assignFrequency（派件故障数）> 3，故障原因
            MailState mailStateAssignFault = mailStateRepository.findMailStateById(9);//返回派件异常状态
            mailRepository.updateAMailAssignFaultAndLastAssignTime(mailStateAssignFault, reason, new Date(), mailId.getId());
            //当assignFrequency（派件故障数）> 3时，修改该邮差的当天的工作量：派件故障量+1，总故障量+1
            int assignFrenquency = mailRepository.findAssignFrequencyById(mailId.getId());
            if(assignFrenquency - 1 == 1)
                str = "forward:/pdetermineassigned";
            else
                str = "forward:/assignException";
            if(assignFrenquency > 3) {
                int mailId1 = mailId.getId();
                Calendar cal = Calendar.getInstance();
                cal.setTime(mailId.getDistributeAssignTime());
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH) + 1;
                int date = cal.get(Calendar.DATE);
                System.out.println(year + " " + month + " " + date);
                workloadRepository.updateWorkloadAssignFaultAndTotalFaultByPostmanAndYearAndMonthAndDate(year, month, date, postman);
                mailRepository.updateAMailDeleteStateAndDetermineStateById(mailId1);//设置邮件为派件不成功和销毁状态(delete_state为1,assign_state为8)
            }
                model.addAttribute("psuccess", "单号:" + mailId.getId() + "--派件故障处理成功！");
                session.setAttribute("page", page);
                return str;

        }
    }
}
