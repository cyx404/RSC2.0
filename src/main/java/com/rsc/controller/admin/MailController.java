package com.rsc.controller.admin;

import com.rsc.entity.Mail;
import com.rsc.entity.Salary;
import com.rsc.repository.MailRepository;
import com.rsc.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class MailController {
    @Autowired
    MailService mailService;
    @Autowired
    MailRepository mailRepository;
    @RequestMapping("mailState")
    public String mailState(Model model, HttpSession session, @RequestParam String state, @RequestParam int i){
        Map<String, List<Mail>> mailAllStateMap = mailService.divideMailState();
        List<Mail> mailList = mailAllStateMap.get(state);
        int n=mailList.size();
        session.setAttribute("page",i);
        session.setAttribute("totalPage",n/5);
        session.setAttribute("state",state);
          if(n<5){
        model.addAttribute("mailAllStateMap",mailList.subList(0,n));}
        else{
            model.addAttribute("mailAllStateMap",mailList.subList(i*5,(i+1)*5));}
        //System.out.println("hhhhh"+mailAllStateMap);
        return "admin/mailState.html";
    }
    @RequestMapping("mailAllState")
    public String mailAllState(){
        return "admin/allMailState.html";
    }

    @RequestMapping("findMailById")
    public String findMailById(Model model, HttpSession session,@RequestParam int mid){
        List<Mail> mailAllStateMap = mailRepository.findMailById1(mid);
        session.setAttribute("page",0);
        session.setAttribute("totalPage",0);
        session.setAttribute("state","");
        model.addAttribute("mailAllStateMap",mailAllStateMap);
        return "admin/mailState.html";
    }



}
