package com.rsc.service;


import com.rsc.controller.admin.Echarts;

import java.util.List;

public interface WorkloadService {
    List<Object[]> findWork(int year, int month, String region);
    //根据地区年月获取订单情况,并返回图表对象
    List<Echarts> findAYMChart(int year,int month);
    //获取工作表中订单所属时间的年份
    List<Integer> findYears();
    //获取某一地区某个月的所有邮差的工作详情(正常)
    List<Object[]> findPostmanWorkDetails(int year,int month,String region);
    //获取某一地区某个月的所有邮差的工作详情（不正常）
    List<Object[]> findErrorPostmanWorkDetails(int year,int month,String region);
}
