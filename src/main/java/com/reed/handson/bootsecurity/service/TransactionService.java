package com.reed.handson.bootsecurity.service;

import com.reed.handson.bootsecurity.domain.Transaction;

import java.util.Optional;

public interface TransactionService {

    Transaction save(Transaction transaction);

    Optional<Transaction> findById(String id);

    Iterable<Transaction> findAll();
}
