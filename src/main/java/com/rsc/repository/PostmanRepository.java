package com.rsc.repository;

import com.rsc.entity.Postman;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostmanRepository extends JpaRepository<Postman,Integer> {

    Postman findPostmanByPhoneAndPassword(String phone, String password);
}
