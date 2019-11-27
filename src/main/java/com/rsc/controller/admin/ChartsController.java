package com.rsc.controller.admin;

import com.rsc.entity.Region;
import com.rsc.repository.RegionRepository;
import com.rsc.service.AttendanceService;
import com.rsc.service.WorkloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("rsc/admin")
@RestController
public class ChartsController {
    @Autowired
    WorkloadService workloadService;
    @Autowired
    AttendanceService attendanceService;
    @Autowired
    RegionRepository regionRepository;

    @RequestMapping(value = "monthWorkData")
    public List<Echarts> find(@RequestParam int year, @RequestParam int month, @RequestParam String region) {
        System.out.println("进来了");
        List<Echarts> list = new ArrayList<Echarts>();
        //Region region1=new Region();
        // region1.setRegion(region);
        List<Object[]> list1 = workloadService.findWork(year, month, region);
        list.add(new Echarts("成功收件量", list1.get(0)[0]));
        list.add(new Echarts("成功派件量", list1.get(0)[1]));
        list.add(new Echarts("失败收件量", list1.get(0)[2]));
        list.add(new Echarts("失败派件量", list1.get(0)[3]));
        return list;

    }

    @RequestMapping(value = "monthWork", method = RequestMethod.GET)
    public ModelAndView firstDemo1(HttpServletRequest request, Model model, HttpSession session) {
        List<Region> regions = regionRepository.findAll();
        List<Integer> years = workloadService.findYears();
        model.addAttribute("years", years);
        model.addAttribute("address", regions);
        return new ModelAndView("admin/MonthWork");
    }

    @RequestMapping(value = "postWordDetailsData")
    public List find1(@RequestParam int year, @RequestParam int month, @RequestParam String region) {
        List<Object[]> list1 = workloadService.findPostmanWorkDetails(year, month, region);
        List postmans = new ArrayList();
        List label = new ArrayList();
        label.add("product");
        label.add("收件计件数");
        label.add("派件计件数");
        label.add("总计数");
        postmans.add(label);
        for (Object[] o : list1) {
            List list = new ArrayList();
            for (Object o1 : o) {
                list.add(o1);
            }
            postmans.add(list);
        }
        return postmans;
    }

    @RequestMapping(value = "postWordDetails", method = RequestMethod.GET)
    public ModelAndView firstDemo11(HttpServletRequest request, Model model, HttpSession session) {
        List<Region> regions = regionRepository.findAll();
        List<Integer> years = workloadService.findYears();
        model.addAttribute("years", years);
        model.addAttribute("address", regions);
        return new ModelAndView("admin/postWordDetails.html");
    }

    @RequestMapping(value = "errorPostWordDetailsData")
    public List find2(@RequestParam int year, @RequestParam int month, @RequestParam String region) {
        List<Object[]> list1 = workloadService.findErrorPostmanWorkDetails(year, month, region);
        List postmans = new ArrayList();
        List label = new ArrayList();
        label.add("product");
        label.add("收件故障量");
        label.add("派件故障量");
        label.add("总故障量");
        postmans.add(label);
        for (Object[] o : list1) {
            List list = new ArrayList();
            for (Object o1 : o) {
                list.add(o1);
            }
            postmans.add(list);
        }
        return postmans;
    }

    @RequestMapping(value = "errorPostWordDetails", method = RequestMethod.GET)
    public ModelAndView firstDemo12(HttpServletRequest request, Model model, HttpSession session) {
        List<Region> regions = regionRepository.findAll();
        List<Integer> years = workloadService.findYears();
        model.addAttribute("years", years);
        model.addAttribute("address", regions);
        return new ModelAndView("admin/ErrorMailDetails.html");
    }

    @RequestMapping(value = "attendanceData")
    public List<Echarts> find3(@RequestParam int year, @RequestParam int month, @RequestParam String name) {
        //  System.out.println(year+month+name);
        System.out.println(attendanceService.findAttendanceByPostmanName(year, month, name));
        return attendanceService.findAttendanceByPostmanName(year, month, name);
    }

    @RequestMapping(value = "attendanceDetails", method = RequestMethod.GET)
    public ModelAndView firstDemo13(HttpServletRequest request, Model model, HttpSession session) {
        List<Integer> years = workloadService.findYears();
        model.addAttribute("years", years);
        return new ModelAndView("admin/PostmanWorkDetails.html");
    }


}
