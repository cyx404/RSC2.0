package com.rsc.service;

import java.util.List;

public interface SalaryService {
    //xiaqi：查询邮差工资详情
    List postmanSalaryDetails(int id);
    int addSalary(int pid,double assessment,double total);
}
