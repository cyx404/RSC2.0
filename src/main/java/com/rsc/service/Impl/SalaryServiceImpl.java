package com.rsc.service.Impl;

import com.rsc.entity.Postman;
import com.rsc.entity.Salary;
import com.rsc.entity.Workload;
import com.rsc.repository.PostmanRepository;
import com.rsc.repository.SalaryRepository;
import com.rsc.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class SalaryServiceImpl implements SalaryService {

    @Autowired
    PostmanRepository postmanRepository;
    @Autowired
    SalaryRepository salaryRepository;

    @Override
    public List postmanSalaryDetails(int id) {
        Calendar date = Calendar.getInstance();
        Postman postman = postmanRepository.findPostmanById(id);
        List detail = new ArrayList();
        detail.add(id);// id:0
        if (postman == null) {
            return null;
        } else {
            detail.add(postman.getName());//名字:1
            detail.add(postman.getPhone());//手机:2
            int receiveWorkloadsum = 0;//收件总和
            int assignWorkloadsum = 0;//派件总和
            for (Workload w : postman.getWorkloads()) {
                receiveWorkloadsum += w.getReceiveWorkload();
                assignWorkloadsum += w.getAssignWorkload();
            }
            detail.add(receiveWorkloadsum);//收件总和:3
            detail.add(assignWorkloadsum);//派件总和:4
            detail.add(receiveWorkloadsum + assignWorkloadsum);//总件数:5
            double total = (receiveWorkloadsum + assignWorkloadsum) * 5 + 3000;
            detail.add("(" + receiveWorkloadsum + "+" + assignWorkloadsum + ")*5+3000=" + total);//总工资:6
            double assessment = (receiveWorkloadsum + assignWorkloadsum) * 5;
            detail.add(assessment);//考核工资:7
            detail.add(postman.getRegion().getRegion());//邮差所在地:8
            Salary salary = salaryRepository.findSalaryByPostmanAndYearAndMonth(id, date.get(Calendar.YEAR), date.get(Calendar.MONTH) + 1);
            if (salary == null) {
                detail.add(1);//查看数据库是否已有数据（没有）:9
            } else {
                detail.add(0);//查看数据库是否已有数据（有）:9
            }
            return detail;
        }
    }

    @Override
    public int addSalary(int pid, double assessment, double total) {
        Salary salary = new Salary();
        Calendar date = Calendar.getInstance();
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        Postman postman = postmanRepository.findPostmanById(pid);
        Salary salary1 = salaryRepository.findSalaryByPostmanAndYearAndMonth(pid, year, month);
        if (salary1 == null) {
            salary.setPostman(postman);
            salary.setAssessment(assessment);
            salary.setBasic(3000);
            salary.setMonth(month);
            salary.setYear(year);
            salary.setTotal(total);
            salaryRepository.save(salary);
        } else {
            salaryRepository.updateSalaryByPidAndYearAndMonth(pid, year, month, 3000, assessment, total);
        }
        return 1;
    }
}
