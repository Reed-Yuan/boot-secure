package com.reed.handson.bootsecurity.service;

import com.reed.handson.bootsecurity.domain.Transaction;

public interface TransactionService {

    Transaction save(Transaction transaction);

    Iterable<Transaction> findAll();
}
