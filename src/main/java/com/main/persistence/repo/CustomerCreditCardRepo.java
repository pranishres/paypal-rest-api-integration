package com.main.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.persistence.entity.CustomerCreditCard;

@Repository
public interface CustomerCreditCardRepo extends JpaRepository<CustomerCreditCard, Integer>{
	public CustomerCreditCard findOneByCustomerIdAndDefaultCard(int customerId, int defaultCard);

}
