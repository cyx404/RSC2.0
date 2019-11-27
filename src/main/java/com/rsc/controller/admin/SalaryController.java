package com.rsc.controller.admin;

import com.rsc.entity.Postman;
import com.rsc.service.PostmanService;
import com.rsc.service.SalaryService;
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
    PostmanService postmanService;
    @Autowired
    SalaryService salaryService;

    @RequestMapping(value = "postsAllSalary")
    public String postmanAllSalary(Model model) {
        List<List> postmanSalaryList = postmanService.findAllPostmanSalary();
        model.addAttribute("salaryList", postmanSalaryList);
        return "admin/postmanSalary.html";
    }

    @RequestMapping(value = "postmanSalary", method = RequestMethod.GET)
    public String postmanSalary(Model model, @RequestParam int id) {
        List salary = salaryService.postmanSalaryDetails(id);
        model.addAttribute("salary", salary);
        return "admin/postmanSalaryDetails.html";
    }

    @RequestMapping(value = "postmanSalary1", method = RequestMethod.POST)
    public String postmanSalary1(Model model, @RequestParam String pname) {
        Postman postman = postmanService.findPostman(pname);
        List salary = salaryService.postmanSalaryDetails(postman.getId());
        model.addAttribute("salary", salary);
        return "admin/postmanSalaryDetails.html";
    }

    @RequestMapping(value = "addSalary", method = RequestMethod.POST)
    public String addSalary(@RequestParam int id, @RequestParam double assessment, @RequestParam double totalMail) {
        double total = totalMail * 5 + 3000;
        salaryService.addSalary(id, assessment, total);
        return "admin/addSuccess.html";
    }
}
