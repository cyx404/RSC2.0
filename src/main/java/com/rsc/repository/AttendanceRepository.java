package com.rsc.repository;


import com.rsc.entity.Attendance;
import com.rsc.entity.Postman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    //根据邮差、年、月返回一个月的考勤记录
    Attendance findAttendanceByPostmanAndYearAndMonth(Postman postman, int year, int month);

    //邮差在岗天数+1
    @Modifying
    @Query("update Attendance as a set a.duty=a.duty+1 where a.postman=?1 and a.year=?2 and a.month=?3 ")
    int updateAttendanceDutyByPostmanAndYearAndMonth(Postman postman, int year, int month);
}
