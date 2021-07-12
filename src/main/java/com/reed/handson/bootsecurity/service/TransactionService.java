package com.reed.handson.bootsecurity.service;

import com.reed.handson.bootsecurity.domain.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TransactionService {

    Transaction save(Transaction transaction);

    Optional<Transaction> findById(String id);

    Iterable<Transaction> findAll();

    List<String> findIdByEmail(final String email);

    List<Transaction> findAllUnPaid();

    Page<Transaction> findUnPaidByEmail(final String email, final Pageable pageable);
}
