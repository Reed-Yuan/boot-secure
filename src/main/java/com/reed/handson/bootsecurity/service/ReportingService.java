package com.reed.handson.bootsecurity.service;

import com.reed.handson.bootsecurity.domain.Transaction;
import com.reed.handson.bootsecurity.repository.TransactionRepository;

public interface ReportingService {
    Iterable<Transaction> findTransactionsByUserName(String firstName, String lastName);
}
