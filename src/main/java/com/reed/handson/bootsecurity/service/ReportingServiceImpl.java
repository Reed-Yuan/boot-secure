package com.reed.handson.bootsecurity.service;

import com.reed.handson.bootsecurity.domain.Transaction;
import com.reed.handson.bootsecurity.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<Transaction> findTransactionsByEmail(final String email, final Pageable pageable) {
        return transactionRepository.findByEmail(email, pageable);
    }

    @Override
    public List<Transaction> findAllUnPaid() {
        return transactionRepository.findAllUnPaid();
    }

    @Override
    public Page<Transaction> findUnPaidByEmail(final String email, final Pageable pageable) {
        return transactionRepository.findUnPaidByEmail(email, pageable);
    }
}
