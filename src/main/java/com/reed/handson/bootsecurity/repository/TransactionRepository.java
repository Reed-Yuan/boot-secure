package com.reed.handson.bootsecurity.repository;

import com.reed.handson.bootsecurity.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

    @Query("select t from Transaction t where t.spender.email = :email")
    List<Transaction> findByEmail(@Param("email") String email);

    @Query("select t from Transaction t where t.state = com.reed.handson.bootsecurity.domain.TransactionState.NOT_PAID")
    List<Transaction> findAllUnPaid();

    @Query("select t from Transaction t where t.state = com.reed.handson.bootsecurity.domain.TransactionState.NOT_PAID and t.spender.email = :email")
    List<Transaction> findUnPaidByEmail(@Param("email") String email);
}
