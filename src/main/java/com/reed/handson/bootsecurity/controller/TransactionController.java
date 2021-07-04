package com.reed.handson.bootsecurity.controller;

import com.reed.handson.bootsecurity.domain.Transaction;
import com.reed.handson.bootsecurity.domain.User;
import com.reed.handson.bootsecurity.service.ReportingService;
import com.reed.handson.bootsecurity.service.TransactionService;
import com.reed.handson.bootsecurity.service.UserService;
import com.reed.handson.bootsecurity.validation.ValidationErrorBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api")
public class TransactionController {

    private final TransactionService transactionService;
    private final UserService userService;
    private final ReportingService reportingService;

    @Autowired
    public TransactionController(TransactionService transactionService, UserService userService, ReportingService reportingService) {
        this.transactionService = transactionService;
        this.userService = userService;
        this.reportingService = reportingService;
    }


    @GetMapping(value = "/debit")
    public ResponseEntity<?> debit() {
        User reed = User.builder().firstName("Reed").lastName("Yuan").build();
        reed = userService.save(reed);
        Transaction transaction = Transaction.builder().spender(reed).amount(100D).build();
        transactionService.save(transaction);
        return ResponseEntity.ok("Debit OK.");

    }

    @GetMapping(value = "/report")
    public Iterable<Transaction> report() {
        User reed = User.builder().firstName("Reed").lastName("Yuan").build();
        reed = userService.save(reed);
        Transaction transaction = Transaction.builder().spender(reed).amount(100D).build();
        transactionService.save(transaction);
        return reportingService.findTransactionsByUserName("Reed", "Yuan");

    }
//    @PostMapping(value = "/debit")
//    public ResponseEntity<?> debit(@Valid @RequestBody Transaction transaction, Errors errors) {
//        if (errors.hasErrors()) {
//            return ResponseEntity.badRequest().
//                    body(ValidationErrorBuilder.fromBindingErrors(errors));
//        }
//        Transaction result = transactionService.save(transaction);
//        URI location = ServletUriComponentsBuilder.fromCurrentRequest().
//                path("/{id}")
//                .buildAndExpand(result.getId()).toUri();
//        return ResponseEntity.created(location).build();
//    }

}
