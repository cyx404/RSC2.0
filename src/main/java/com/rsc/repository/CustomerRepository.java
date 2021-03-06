package com.rsc.repository;


import com.rsc.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    Customer findCustomerByPhone(String phone);

    Customer findCustomerByPhoneAndPassword(String phone,String password);
}
