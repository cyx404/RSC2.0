package com.rsc.repository;


import com.rsc.entity.Attendance;
import com.rsc.entity.Postman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    //根据邮差、年、月返回一个月的考勤记录
    Attendance findAttendanceByPostmanAndYearAndMonth(Postman postman, int year, int month);

    //邮差在岗天数+1
    @Modifying
    @Query("update Attendance as a set a.duty=a.duty+1 where a.postman=?1 and a.year=?2 and a.month=?3 ")
    int updateAttendanceDutyByPostmanAndYearAndMonth(Postman postman, int year, int month);

    //xiaqi:根据邮差、年、月返回一个月的考勤记录
    @Query("select a from Attendance a where a.year=?1 and a.month=?2 and a.postman.name=?3")
    Attendance findAttendanceByPostmanAndYearAndMonth1(int year, int month,String name);

    //xiaqi:通过id查找员工的考勤情况
    @Query("select a from Attendance a where a.postman.id=?1")
    List<Attendance> findAttendancesByPostman(int pid);

    //xiaqi：某一员工请假天数加一
    @Transactional
    @Modifying
    @Query("update Attendance as a set a.leaves=a.leaves+1 where a.postman.id=?1 and a.year=?2 and a.month=?3")
    int updateAttendanceLeavesByPostmanIdAndYearAndMonth(int id, int year, int month);

    //xiaqi：某一员工加班天数加一
    @Transactional
    @Modifying
    @Query("update Attendance as a set a.overtime=a.overtime+1 where a.postman.id=?1 and a.year=?2 and a.month=?3")
    int updateAttendanceOvertimeByPostmanIdAndYearAndMonth(int id, int year, int month);
}
