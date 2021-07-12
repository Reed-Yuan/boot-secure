package com.reed.handson.bootsecurity.service;

import com.reed.handson.bootsecurity.domain.Transaction;
import com.reed.handson.bootsecurity.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
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

    @Override
    public List<String> findIdByEmail(final String email) {
        return transactionRepository.findIdByEmail(email);
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
