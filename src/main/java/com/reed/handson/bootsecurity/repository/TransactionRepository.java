package com.reed.handson.bootsecurity.repository;

import com.reed.handson.bootsecurity.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
