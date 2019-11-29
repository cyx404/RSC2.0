package com.rsc.service.Impl;

import com.rsc.entity.*;
import com.rsc.repository.MailRepository;
import com.rsc.repository.MailStateRepository;
import com.rsc.repository.RegionRepository;
import com.rsc.repository.WorkloadRepository;
import com.rsc.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @ClassName:AdminService
 * @Description:TODO
 * @Author:chenyx
 * @Date:Create in  2019/11/20 0:55
 **/
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private MailRepository mailRepository;

    @Autowired
    private MailStateRepository mailStateRepository;

    @Autowired
    private WorkloadRepository workloadRepository;

    //当前的日期
    private Calendar cal = Calendar.getInstance();
    private int year = cal.get(Calendar.YEAR);
    private int month = cal.get(Calendar.MONTH) + 1;
    private int date = cal.get(Calendar.DATE);


    /**
     * @Title distributeReceivingWorksToPostmans
     * @Description: TODO 分配收件工作给已经签到上班的快递员
     * @param model
     * @return String
     * @Author: chenyx
     * @Date: 2019/11/20  0:58
     **/
    @Override
    @Transactional
    public String distributeReceivingWorksToPostmans(Model model) {

        //返回要分配的区
        List<Region> regions = regionRepository.findAll();

        //状态
        MailState mailStateWaitingDistribution = mailStateRepository.findMailStateById(0);//返回“等待分配”状态
        MailState mailStateReadyingReceive = mailStateRepository.findMailStateById(1);//返回“准备收件”状态

        String s="";
        //所有的区都分配一次
        for (Region region : regions) {
            System.out.println("<center><br>地区：" + region.getRegion() + " 编号：" + region.getId());
            //xiaqi：改
           // s = s+ "地区：" +region.getRegion() + " 编号：" + region.getId()+"---->";
            s ="<center>"+s+ "地区：" +region.getRegion() + " 编号：" + region.getId()+"---->";
            //返回某地区所有收件状态为“等待分配”的件
            List<Mail> remailList = mailRepository.findMailByRegionAndReceiveStateIsWaitingDistribution(region, mailStateWaitingDistribution);

            //返回某地区的已经签到上班的邮差的工作量
            List<Workload> workloadList = workloadRepository.findWorkloadByAlreadyOnduty(year, month, date, region);

            //根据上班的邮差人数分配邮件
            Postman postman;
            Mail mail;
            int postmanIdnex = 0;//List中第一个邮差的下标
            int remailnum = remailList.size();//需要分配的收件的数量
            System.out.println("需要分配的收件的数量:" + remailnum);
            s = s+ "需要分配的收件的数量:" +remailnum+"---->";
            int postmannum = workloadList.size();//上班邮差人数，一个人当天生成一条工作量，可以通过当天多少条工作量得出多少人上班
            System.out.println("上班邮差人数:" + postmannum);
            s = s+ "上班邮差人数:" +postmannum+"<br><br>";
            System.out.println("----------------");

            //开始分配收件工作，一人一个收件的邮件循环分
            if (0 != remailnum && 0 != postmannum) {
                for (int i = 0; i < remailnum; i++) {
                    postman = workloadList.get(postmanIdnex).getPostman();
                    mail = remailList.get(i);
                    mailRepository.addAMailReceivePostman(postman, mailStateReadyingReceive, new Date(), mail);//根据寄件给它分配收件员,收件状态变成”准备收件“,设置系统分配收件时间
                    workloadRepository.updateWorkloadExpectationWorkloadByPostmanAndYearAndMonthAndDate(year, month, date, postman);//收件员预期工作总量+1
                    if (0 == (i + 1) % postmannum) {
                        postmanIdnex = 0;
                    } else {
                        postmanIdnex++;
                    }
                }
            }

        }
        //xiaqi：改
       // s=s+"----------收件工作分配完成！------------<br>";
        s=s+"----------收件工作分配完成！------------<br></center>";
       //xiaqi： s=s+" <center><a href=\"admin\">返回首页</a></center>";
        //  model.addAttribute("asuccess", "收件工作分配完成！");
        // return "admin/success";
        return s;
    }


    /**
     * @Title distributeAssigningWorksToPostmans
     * @Description: TODO 分配派件工作给已经签到上班的快递员
     * @param model
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/11/20  19:06
     **/
    @Override
    @Transactional
    public String distributeAssigningWorksToPostmans(Model model) {

        //返回要分配的区
        List<Region> regions = regionRepository.findAll();

        //状态
        MailState mailStateWaitingDistribution = mailStateRepository.findMailStateById(0);//返回“等待分配”状态
        MailState mailStateReadyingAssign = mailStateRepository.findMailStateById(5);//返回“准备派件”状态

        String s="";

        //所有的区都分配一次
        for (Region region : regions) {
            System.out.println("<br>地区：" + region.getRegion() + " 编号：" + region.getId());
            //xiaqi：改
           //s = s+ "地区：" +region.getRegion() + " 编号：" + region.getId()+"---->";
            s ="<center>"+ s+ "地区：" +region.getRegion() + " 编号：" + region.getId()+"---->";


            //返回某地区所有派件状态为“等待分配”的件
            List<Mail> asmailList = mailRepository.findMailByRegionAndAssignStateIsWaitingDistribution(region, mailStateWaitingDistribution);

            //返回某地区的已经签到上班的邮差的工作量
            List<Workload> workloadList = workloadRepository.findWorkloadByAlreadyOnduty(year, month, date, region);

            //根据上班的邮差人数分配邮件
            Postman postman;
            Mail mail;
            int postmanIdnex = 0;//List中第一个邮差的下标
            int asmailnum = asmailList.size();//需要分配的派件的数量
            System.out.println("需要分配的派件的数量:" + asmailnum);
            s = s+ "需要分配的收件的数量:" +asmailnum+"---->";
            int postmannum = workloadList.size();//上班邮差人数，一个人当天生成一条工作量，可以通过当天多少条工作量得出多少人上班
            System.out.println("上班邮差人数:" + postmannum);
            s = s+ "上班邮差人数:" +postmannum+"<br><br>";
            System.out.println("----------------");

            //开始分配派件工作，一人一个派件的邮件循环分
            if (0 != asmailnum && 0 != postmannum) {
                for (int i = 0; i < asmailnum; i++) {
                    postman = workloadList.get(postmanIdnex).getPostman();
                    mail = asmailList.get(i);
                    mailRepository.addAMailAssignPostman(postman, mailStateReadyingAssign, new Date(), mail); //根据寄件给它分配派件员,派件状态变成”准备派件“,设置系统分配派件时间
                    workloadRepository.updateWorkloadExpectationWorkloadByPostmanAndYearAndMonthAndDate(year, month, date, postman);//派件员预期工作总量+1
                    if (0 == (i + 1) % postmannum) {
                        postmanIdnex = 0;
                    } else {
                        postmanIdnex++;
                    }
                }
            }

        }
        //xiaqi:改
        //s=s+"----------派件工作分配完成！------------<br>";
        s=s+"----------派件工作分配完成！------------<br></center>";
        //xiaqi：s=s+"<center><a href=\"admin\">返回首页</a></center>";
        //model.addAttribute("asuccess", "派件工作分配完成！");
        // return "admin/success";
        return s;
    }
}
