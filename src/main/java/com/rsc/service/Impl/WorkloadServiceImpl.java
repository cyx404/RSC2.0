package com.rsc.service.Impl;

import com.rsc.controller.admin.Echarts;
import com.rsc.repository.WorkloadRepository;
import com.rsc.service.WorkloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkloadServiceImpl implements WorkloadService {
    @Autowired
    WorkloadRepository workloadRepository;

    @Override
    public List<Object[]> findWork(int year, int month, String region) {
        List<Object[]> list = workloadRepository.findByYearAndAndMonth(year, month, region);

        return list;
    }

    @Override
    public List<Echarts> findAYMChart(int year, int month) {
        return null;
    }

    @Override
    public List<Integer> findYears() {
        List<Integer> years = new ArrayList<>();
        List<Object[]> list = workloadRepository.findGroupByYear();
        for (int i = 0; i < list.get(0).length; i++) {
            years.add((Integer) list.get(0)[i]);
        }
        System.out.println(years.toString());
        return years;
    }

    @Override
    public List<Object[]> findPostmanWorkDetails(int year, int month, String region) {
        //  System.out.println("hhhhhhhh");
        return workloadRepository.findDetailsByRegion(year, month, region);
    }

    @Override
    public List<Object[]> findErrorPostmanWorkDetails(int year, int month, String region) {
        return workloadRepository.findErrorDetailsByRegion(year, month, region);
    }
}
