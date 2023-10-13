package com.example.Swiggato.repository;

import com.example.Swiggato.model.DeliveryPartner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryPartnerRepository extends JpaRepository<DeliveryPartner,Integer> {

    public DeliveryPartner findByMobileNo(String mobile);

    //HQL query

    String preparedQuery ="select p from DeliveryPartner p order by rand() LIMIT 1";
    @Query(value = preparedQuery)
   public DeliveryPartner findRandomDeliveryPartner();
}
