package com.reed.handson.bootsecurity.service;

import com.reed.handson.bootsecurity.domain.Transaction;

public interface ReportingService {
    Iterable<Transaction> findTransactionsByUserName(String name);
}
