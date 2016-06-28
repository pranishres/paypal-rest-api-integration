package com.main.persistence.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.persistence.entity.BillingAddress;

@Repository
public interface BillingAddressRepo extends JpaRepository<BillingAddress, Integer>{
	
}
