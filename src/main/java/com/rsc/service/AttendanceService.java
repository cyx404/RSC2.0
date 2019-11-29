package com.rsc.service;

import com.rsc.controller.admin.Echarts;

import java.util.List;

public interface AttendanceService {
    //xiaqi：根据时间和邮差查询考勤情况
    List<Echarts> findAttendanceByPostmanName(int year, int month, int id);
}
