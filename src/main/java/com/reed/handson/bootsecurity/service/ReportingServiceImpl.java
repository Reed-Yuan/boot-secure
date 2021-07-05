package com.reed.handson.bootsecurity.service;

import com.reed.handson.bootsecurity.domain.Transaction;
import com.reed.handson.bootsecurity.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportingServiceImpl implements ReportingService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public ReportingServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Iterable<Transaction> findTransactionsByEmail(final String email) {
        return transactionRepository.findByEmail(email);
    }

    @Override
    public List<Transaction> findAllUnPaid() {
        return transactionRepository.findAllUnPaid();
    }
}
