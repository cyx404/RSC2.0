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

    //邮差收件故障量+1，总故障量+1
   @Modifying
   @Query("update Workload as w set w.receiveFault=w.receiveFault+1 ,w.totalFault=w.totalFault+1  where w.year=?1 and w.month=?2 and w.date=?3 and w.postman=?4")
    int updateWorkloadReceiveFaultAndTotalFaultdByPostmanAndYearAndMonthAndDate(int year, int month, int date, Postman postman);

    //返回某地区的已经签到上班的邮差的工作量
    @Query("select w from Workload  w where w.year=?1 and w.month=?2 and w.date=?3 and w.postman in (select p from Postman p where p.region=?4)")
    List<Workload> findWorkloadByAlreadyOnduty(int year,int month,int date,Region region,Pageable pageable);

    //xiaqi：按月份统计工作量（邮件派件量、收件量及故障件数）
    @Query("select sum(w.receiveWorkload),sum(w.assignWorkload),sum(w.receiveFault),sum(w.assignFault) from Workload w where w.year=?1 and w.month=?2 and w.postman in (select p from  Postman p where p.region.region=?3)")
    List<Object[]> findByYearAndAndMonth(int year,int month,String region);

    //xiaqi:查询所有的应有的年份
    @Query("select w.year from Workload w group by w.year")
    List<Object[]> findGroupByYear();
    //邮差派件故障量+1，总故障量+1
    @Modifying
    @Query("update Workload as w set w.assignFault=w.assignFault+1 ,w.totalFault=w.totalFault+1  where w.year=?1 and w.month=?2 and w.date=?3 and w.postman=?4")
    int updateWorkloadAssignFaultAndTotalFaultByPostmanAndYearAndMonthAndDate(int year, int month, int date, Postman postman);

    //xiaqi:按地区月份查找每个邮差的工作情况(正常）
    @Query("select w.postman.name,sum(w.receiveWorkload),sum(w.assignWorkload),sum(w.totalWorkload) from Workload w where w.year=?1 and w.month=?2 and w.postman in (select p from Postman p where p.region.region=?3) group by w.postman order by sum(w.totalWorkload) DESC")
    List<Object[]> findDetailsByRegion(int year,int month,String region);

    //xiaqi:按地区月份查找每个邮差的工作情况(不正常）
    @Query("select w.postman.name,sum(w.receiveFault),sum(w.assignFault),sum(w.totalFault) from Workload w where w.year=?1 and w.month=?2 and w.postman in (select p from Postman p where p.region.region=?3) group by w.postman order by sum(w.totalFault) DESC")
    List<Object[]> findErrorDetailsByRegion(int year,int month,String region);

    //邮差派件量+1，总工作总量+1
    @Modifying
    @Query("update Workload as w set w.assignWorkload=w.assignWorkload+1 ,w.totalWorkload=w.totalWorkload+1  where w.year=?1 and w.month=?2 and w.date=?3 and w.postman=?4")
    int updateWorkloadAssignWorkloadAndTotalWorkloadByPostmanAndYearAndMonthAndDate(int year, int month, int date, Postman receivePostman);
}
