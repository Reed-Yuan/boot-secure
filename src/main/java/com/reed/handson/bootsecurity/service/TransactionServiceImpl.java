package com.reed.handson.bootsecurity.service;

import com.reed.handson.bootsecurity.domain.Transaction;
import com.reed.handson.bootsecurity.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(final TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Optional<Transaction> findById(String id) {
        return transactionRepository.findById(id);
    }

    @Override
    public Iterable<Transaction> findAll() {
        return transactionRepository.findAll();
    }
}
