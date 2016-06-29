package com.main.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.persistence.entity.Transactions;

@Repository
public interface TransactionRepo extends JpaRepository<Transactions, Integer>{

}
