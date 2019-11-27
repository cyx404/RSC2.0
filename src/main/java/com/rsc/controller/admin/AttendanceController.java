package com.rsc.controller.admin;

import com.rsc.entity.Attendance;
import com.rsc.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class AttendanceController {
    @Autowired
    AttendanceRepository attendanceRepository;
    @RequestMapping(value = "leaves")
    public String addLeaves(HttpSession session, @RequestParam int pid, @RequestParam int year, @RequestParam int month){
     //   System.out.println("id"+pid+"year"+year+"month"+month);
        attendanceRepository.updateAttendanceLeavesByPostmanIdAndYearAndMonth(pid,year,month);
        return "redirect:/attendance";

    }

    @RequestMapping(value = "overtime")
    public String addOvertime(HttpSession session, @RequestParam int pid, @RequestParam int year, @RequestParam int month){
       // System.out.println("id"+pid+"year"+year+"month"+month);
        attendanceRepository.updateAttendanceOvertimeByPostmanIdAndYearAndMonth(pid,year,month);
        return "redirect:/attendance";

    }

    @RequestMapping(value = "attendance")
    public String attendance(HttpSession session){
       // attendanceRepository.updateAttendanceLeavesByPostmanAndYearAndMonth(pid,year,month);
        if(session.getAttribute("mid")!=null){
            session.setAttribute("attendences",attendanceRepository.findAttendancesByPostman((Integer) session.getAttribute("mid")));
        }
        return "admin/attendance.html";
    }
    @RequestMapping(value = "findPostmanAttendance")
    public String findPostmanAttendance(HttpSession session,@RequestParam int mid){
        // attendanceRepository.updateAttendanceLeavesByPostmanAndYearAndMonth(pid,year,month);
        session.setAttribute("mid",mid);
        session.setAttribute("attendences",attendanceRepository.findAttendancesByPostman(mid));
        return "admin/attendance.html";
    }

    @RequestMapping(value = "test1")
    public String test1(){
        return "admin/test1.html";
    }


}