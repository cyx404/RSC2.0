package com.rsc.repository;


import com.rsc.entity.MailState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MailStateRepository  extends JpaRepository<MailState,Integer> {

    MailState findMailStateById(int id);


}
