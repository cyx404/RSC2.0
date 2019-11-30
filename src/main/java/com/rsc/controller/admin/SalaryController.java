package com.rsc.controller.admin;

import com.rsc.service.PostmanService;
import com.rsc.service.SalaryService;
import com.rsc.service.WorkloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("rsc/admin")
@Controller
public class SalaryController {

    @Autowired
    private PostmanService postmanService;
    @Autowired
    private SalaryService salaryService;
    @Autowired
    private WorkloadService workloadService;

    @RequestMapping(value = "postsAllSalary")
    public String postmanAllSalary(Model model,@RequestParam int year,@RequestParam int month) {
        List<Integer> years = workloadService.findYears();
        model.addAttribute("years", years);
        List<List> postmanSalaryList = postmanService.findAllPostmanSalary(year,month);
        if (postmanSalaryList.size()==0){
            model.addAttribute("error", "该月没有员工工资信息");
        }else{
        model.addAttribute("year",year);
        model.addAttribute("month",month);
        model.addAttribute("salaryList", postmanSalaryList);}
        return "admin/postmanSalary.html";
    }
    @RequestMapping(value = "postsAllSalary1")
    public String postmanAllSalary1(Model model){
        List<Integer> years = workloadService.findYears();
        model.addAttribute("years", years);
        return "admin/postmanSalary.html";
    }


    @RequestMapping(value = "postmanSalary", method = RequestMethod.GET)
    public String postmanSalary(Model model){
        List<Integer> years = workloadService.findYears();
        model.addAttribute("years", years);
        return "admin/postmanSalaryDetails.html";
    }

    @RequestMapping(value = "postmanSalary1")
    public String postmanSalary1(Model model, @RequestParam int pid,@RequestParam int year,@RequestParam int month,@RequestParam int tag) {
        List salary = salaryService.postmanSalaryDetails(pid,year,month);
        if (salary == null) {
            model.addAttribute("error", "没有该员工的信息");
            model.addAttribute("tag",tag);
            return "admin/postmanSalaryDetails.html";
        } else {
            model.addAttribute("tag",tag);
            model.addAttribute("salary", salary);
            return "admin/postmanSalaryDetails.html";
        }
    }
    @RequestMapping(value = "addSalary", method = RequestMethod.POST)
    public String addSalary(Model model,@RequestParam int id, @RequestParam double assessment, @RequestParam double totalMail,@RequestParam int year,@RequestParam int month) {
        double total = totalMail * 5 + 3000;
        salaryService.addSalary(id, assessment, total,year,month);
        model.addAttribute("tag",1);
        return "admin/addSuccess.html";
    }

    @RequestMapping(value = "addAllSaray")
    public String addAllSalary(Model model,@RequestParam int year,@RequestParam int month){
        List<List> postmanSalaryList = postmanService.findAllPostmanSalary(year,month);
        for (List l1:postmanSalaryList) {
            System.out.println("aaaaa"+l1.get(0)+","+l1.get(3)+","+l1.get(2));
            salaryService.addSalary((int)l1.get(0), (double)l1.get(3), (double)l1.get(2),year,month);
        }
        model.addAttribute("tag",0);
        return "admin/addSuccess.html";
    }
}
