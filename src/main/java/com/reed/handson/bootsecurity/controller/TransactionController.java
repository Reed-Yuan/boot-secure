package com.reed.handson.bootsecurity.controller;

import com.reed.handson.bootsecurity.domain.Transaction;
import com.reed.handson.bootsecurity.domain.TransactionState;
import com.reed.handson.bootsecurity.domain.User;
import com.reed.handson.bootsecurity.domain.UserRole;
import com.reed.handson.bootsecurity.service.ReportingService;
import com.reed.handson.bootsecurity.service.TransactionService;
import com.reed.handson.bootsecurity.service.UserService;
import com.reed.handson.bootsecurity.validation.ValidationErrorBuilder;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/transaction")
public class TransactionController extends SecuredController {

    private final TransactionService transactionService;
    private final ReportingService reportingService;

    @Autowired
    public TransactionController(TransactionService transactionService, UserService userService, ReportingService reportingService) {
        super(userService);
        this.transactionService = transactionService;
        this.reportingService = reportingService;
    }

    @Autowired
    private Validator validator;

    @PostMapping(value = "/debit")
    @Operation(summary = "Make a loan, maximum $1000")
    public ResponseEntity<?> debit(@RequestParam Double amount) {

        User user = getUser();
        if (!user.isEnabled()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User account locked");
        }
        Transaction transaction = Transaction.builder()
                .spender(user).amount(amount).state(TransactionState.NOT_PAID).build();
        Set<ConstraintViolation<Transaction>> errors = validator.validate(transaction);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().
                    body(ValidationErrorBuilder.fromValidationErrors(errors));
        }
        transaction = transactionService.save(transaction);
        return ResponseEntity.ok(transaction);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Check a single transaction, STAFF can check any, ADMIN or USER can check their own transactions")
    public ResponseEntity<?> getState(@PathVariable String id) {
        User user = getUser();
        Optional<Transaction> transactionOpt = transactionService.findById(id);

        if (!transactionOpt.isPresent()) return ResponseEntity.badRequest().body("Transaction not found");

        Transaction transaction = transactionOpt.get();

        if (user.getRole() == UserRole.STAFF || transaction.getSpender().getEmail().equals(user.getEmail())) {
            return ResponseEntity.ok(transaction);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not authorized");
        }
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Update transaction state (PAID/NOT_PAID), STAFF only")
    public ResponseEntity<?> updateState(@PathVariable String id, @RequestParam String state) {
        TransactionState newState;
        switch (state.toUpperCase()) {
            case "PAID":
                newState = TransactionState.PAID;
                break;
            case "NOT_PAID":
                newState = TransactionState.NOT_PAID;
                break;
            default:
                return ResponseEntity.badRequest().body("Invalid state");
        }

        User user = getUser();
        Optional<Transaction> transactionOpt = transactionService.findById(id);

        if (!transactionOpt.isPresent()) return ResponseEntity.badRequest().body("Transaction not found");

        Transaction transaction = transactionOpt.get();

        if (user.getRole() == UserRole.STAFF) {
            transaction.setState(newState);
            transaction = transactionService.save(transaction);
            return ResponseEntity.ok(transaction);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not authorized");
        }
    }


}
