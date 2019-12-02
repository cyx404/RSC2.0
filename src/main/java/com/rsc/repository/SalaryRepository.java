package com.rsc.repository;

import com.rsc.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface SalaryRepository extends JpaRepository<Salary,Integer> {

    @Transactional
    @Modifying
    @Query("update Salary as s set s.basic=?4,s.assessment=?5,s.total=?6 where s.postman.id=?1 and s.year=?2 and s.month=?3")
    int updateSalaryByPidAndYearAndMonth(int pid,int year,int month,double basic,double assessment,double total);

    @Query("select s from Salary s where s.postman.id=?1 and s.year=?2 and s.month=?3")
    Salary findSalaryByPostmanAndYearAndMonth(int id,int year,int month);

}
