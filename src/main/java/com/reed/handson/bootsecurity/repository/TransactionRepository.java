package com.reed.handson.bootsecurity.repository;

import com.reed.handson.bootsecurity.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

    @Query("select t from Transaction t where t.spender.name = :name")
    List<Transaction> findByUserName(@Param("name") String name);
}
