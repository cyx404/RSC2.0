package com.rsc.controller.admin;

import com.rsc.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@RequestMapping("rsc/admin")
@Controller
public class AttendanceController {

    @Autowired
   private AttendanceRepository attendanceRepository;

    @RequestMapping(value = "leaves")
    public String addLeaves(HttpSession session, @RequestParam int pid, @RequestParam int year, @RequestParam int month) {
        //   System.out.println("id"+pid+"year"+year+"month"+month);
        attendanceRepository.updateAttendanceLeavesByPostmanIdAndYearAndMonth(pid, year, month);
        return "redirect:/rsc/admin/attendance";

    }

    @RequestMapping(value = "overtime")
    public String addOvertime(HttpSession session, @RequestParam int pid, @RequestParam int year, @RequestParam int month) {
        // System.out.println("id"+pid+"year"+year+"month"+month);
        attendanceRepository.updateAttendanceOvertimeByPostmanIdAndYearAndMonth(pid, year, month);
        return "c";

    }

    @RequestMapping(value = "attendance")
    public String attendance(HttpSession session) {
        // attendanceRepository.updateAttendanceLeavesByPostmanAndYearAndMonth(pid,year,month);
        if (session.getAttribute("mid") != null) {
            session.setAttribute("attendences", attendanceRepository.findAttendancesByPostman((Integer) session.getAttribute("mid")));
        }
        return "admin/attendance.html";
    }

    @RequestMapping(value = "findPostmanAttendance")
    public String findPostmanAttendance(Model model, @RequestParam int mid) {
        // attendanceRepository.updateAttendanceLeavesByPostmanAndYearAndMonth(pid,year,month);
        model.addAttribute("mid", mid);
        model.addAttribute("attendences", attendanceRepository.findAttendancesByPostman(mid));
        return "admin/attendance.html";
    }


}
