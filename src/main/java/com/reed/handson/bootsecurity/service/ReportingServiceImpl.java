package com.reed.handson.bootsecurity.service;

import com.jayway.jsonpath.internal.Utils;
import com.reed.handson.bootsecurity.domain.Transaction;
import com.reed.handson.bootsecurity.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportingServiceImpl implements ReportingService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public ReportingServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Iterable<Transaction> findTransactionsByUserName(final String name) {
        return transactionRepository.findByUserName(name);
    }
}
