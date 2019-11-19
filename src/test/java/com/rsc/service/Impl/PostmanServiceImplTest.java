package com.rsc.service.Impl;

import com.rsc.repository.WorkloadRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PostmanServiceImplTest {

    @Autowired
    private WorkloadRepository workloadRepository;

    @Test
    public void selectPostmantoWork() {
//        Workload workload4=workloadRepository.findFirstWorkloadByYearAndMonthAndDateOrderByTotalWorkloadAsc(2019,11,15);
//        System.out.println("======"+workload4.getId());

    }
}