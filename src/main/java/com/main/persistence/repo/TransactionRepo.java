package com.main.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.persistence.entity.Transaction;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Integer>{

}
