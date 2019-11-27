package com.rsc.repository;


import com.rsc.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ManagerRepository extends JpaRepository<Manager, Integer> {
    //xiaqi
    Manager findManagerByMphoneAndMpassword(String phone,String password);
}
