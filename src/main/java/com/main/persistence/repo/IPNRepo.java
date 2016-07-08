package com.main.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.persistence.entity.IPN;

@Repository
public interface IPNRepo extends JpaRepository<IPN, String> {

}
