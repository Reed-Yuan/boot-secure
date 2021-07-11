package com.reed.handson.bootsecurity.service;

import com.reed.handson.bootsecurity.domain.Transaction;

import java.util.List;

public interface ReportingService {
    Iterable<Transaction> findTransactionsByEmail(String email);
    List<Transaction> findAllUnPaid();
    List<Transaction> findUnPaidByEmail(final String email);
}
