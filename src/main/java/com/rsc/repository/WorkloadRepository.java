package com.rsc.repository;

import com.rsc.entity.Postman;
import com.rsc.entity.Region;
import com.rsc.entity.Workload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface WorkloadRepository extends JpaRepository<Workload, Integer> {

    //找某一天的某个邮差是否已经打卡上班
    Workload findWorkloadByPostmanAndYearAndMonthAndDate(Postman postman, int year, int month, int date);

    //寻找邮差去送快递
    @Query("select w from Workload w where  w.year=?1 and w.month=?2 and w.date=?3 and w.postman in (select p from Postman p where p.region=?4) ")
    Page<Workload> findFirstWorkloadtoDelivery(int year, int month, int date, Region region, Pageable pageable);

    //邮差预期工作总量+1
    @Modifying
    @Query("update Workload as w set w.expectationWorkload = w.expectationWorkload+1 where w.year=?1 and w.month=?2 and w.date=?3 and w.postman=?4")
    int updateWorkloadExpectationWorkloadByPostmanAndYearAndMonthAndDate(int year, int month, int date, Postman postman);

    //邮差收件量+1，总工作总量+1
    @Modifying
    @Query("update Workload as w set w.receiveWorkload=w.receiveWorkload+1 ,w.totalWorkload=w.totalWorkload+1  where w.year=?1 and w.month=?2 and w.date=?3 and w.postman=?4")
    int updateWorkloadReceiveWorkloadAndTotalWorkloadByPostmanAndYearAndMonthAndDate(int year, int month, int date, Postman postman);

    //邮差收件故障量+1，派件故障量+1
   @Modifying
   @Query("update Workload as w set w.receiveFault=w.receiveFault+1 ,w.totalFault=w.totalFault+1  where w.year=?1 and w.month=?2 and w.date=?3 and w.postman=?4")
    int updateWorkloadReceiveFaultAndTotalFaultdByPostmanAndYearAndMonthAndDate(int year, int month, int date, Postman postman);

    //返回某地区的已经签到上班的邮差的工作量
    @Query("select w from Workload  w where w.year=?1 and w.month=?2 and w.date=?3 and w.postman in (select p from Postman p where p.region=?4)")
    List<Workload> findWorkloadByAlreadyOnduty(int year,int month,int date,Region region);



}
