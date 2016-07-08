package com.main.persistence.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.persistence.entity.BillingAgreement;

@Repository
public interface BillingAgreementRepo extends JpaRepository<BillingAgreement , Integer>{
	public BillingAgreement findOneByToken(String token);
	
	public BillingAgreement findOneByBillingAgreementId(String billingAgreementId);
	
	public Page<BillingAgreement> findAllByCustomerId(int customerId, Pageable pageable);
	
	public List<BillingAgreement> findAllByCustomerId(int customerId);
	
}
