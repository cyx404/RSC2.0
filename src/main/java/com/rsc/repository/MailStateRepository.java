package com.rsc.repository;


import com.rsc.entity.MailState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailStateRepository  extends JpaRepository<MailState,Integer> {

    MailState findMailStateById(int id);


}
