package com.reed.handson.bootsecurity.service;

import com.reed.handson.bootsecurity.domain.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReportingService {
    Page<Transaction> findTransactionsByEmail(final String email, final Pageable pageable);
    List<Transaction> findAllUnPaid();
    Page<Transaction> findUnPaidByEmail(final String email, final Pageable pageable);
}
