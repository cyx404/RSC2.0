package com.rsc.repository;

import com.rsc.entity.Postman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostmanRepository extends JpaRepository<Postman,Integer> {

    Postman findPostmanByPhoneAndPassword(String phone, String password);

    //xiaqi:查出所有邮差
    @Query("select p from Postman p")
    List<Postman> findAllPostman();

    //xiaqi:查询某一邮差
    @Query("select p from Postman  p where p.id=?1")
    Postman findPostmanById(int id);

    //xiaqi:查询某一邮差
    @Query("select p from Postman p where p.name=?1")
    Postman findPostmanByName(String name);
}
