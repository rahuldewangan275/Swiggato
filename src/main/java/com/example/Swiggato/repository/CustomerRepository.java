package com.example.Swiggato.repository;

import com.example.Swiggato.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
  @Query(value = "SELECT * FROM customer WHERE phone_no = :mobile",nativeQuery = true)
  public  Customer findByPhoneNo(String mobile);
}
