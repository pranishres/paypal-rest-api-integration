package com.main.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.persistence.entity.BillingAgreement;

@Repository
public interface BillingAgreementRepo extends JpaRepository<BillingAgreement , Integer>{
	public BillingAgreement findOneByToken(String token);
}
