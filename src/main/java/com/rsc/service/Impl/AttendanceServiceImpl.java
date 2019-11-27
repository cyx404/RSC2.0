package com.rsc.service.Impl;

import com.rsc.controller.admin.Echarts;
import com.rsc.entity.Attendance;
import com.rsc.repository.AttendanceRepository;
import com.rsc.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    @Autowired
    AttendanceRepository attendanceRepository;
    @Override
    public List<Echarts> findAttendanceByPostmanName(int year, int month, String postmanName) {
        Attendance attendance = attendanceRepository.findAttendanceByPostmanAndYearAndMonth1(year,month,postmanName);
        List<Echarts> list = new ArrayList<Echarts>();
        if(attendance==null) list.add(new Echarts("无数据",0));
        else {
            if (attendance.getDuty() != 0) {
                list.add(new Echarts("在岗天数", attendance.getDuty()));
            }
            if (attendance.getLeaves() != 0) {
                list.add(new Echarts("请假天数", attendance.getLeaves()));
            }
            if (attendance.getOvertime() != 0) {
                list.add(new Echarts("加班天数", attendance.getOvertime()));
            }

        }
        return list;
    }
}
