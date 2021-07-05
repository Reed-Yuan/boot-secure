package com.reed.handson.bootsecurity.controller;

import com.reed.handson.bootsecurity.domain.Transaction;
import com.reed.handson.bootsecurity.domain.TransactionState;
import com.reed.handson.bootsecurity.domain.User;
import com.reed.handson.bootsecurity.service.ReportingService;
import com.reed.handson.bootsecurity.service.TransactionService;
import com.reed.handson.bootsecurity.service.UserService;
import com.reed.handson.bootsecurity.validation.ValidationError;
import com.reed.handson.bootsecurity.validation.ValidationErrorBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

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

    @Autowired
    private Validator validator;

    @GetMapping(value = "/debit")
    public ResponseEntity<?> debit(@RequestParam Double amount) {
        User reed = User.builder().name("Reed").build();
        reed = userService.save(reed);
        Transaction transaction = Transaction.builder()
                .spender(reed).amount(amount).state(TransactionState.NOT_PAID).build();
        Set<ConstraintViolation<Transaction>> errors = validator.validate(transaction);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().
                    body(ValidationErrorBuilder.fromValidationErrors(errors));
        }
        transactionService.save(transaction);
        return ResponseEntity.ok("Debit " + amount + " completed.");
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ValidationError handleException(Exception exception) {
        return new ValidationError(exception.getMessage());
    }
}
