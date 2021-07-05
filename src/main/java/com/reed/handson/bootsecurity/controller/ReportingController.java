package com.reed.handson.bootsecurity.controller;

import com.reed.handson.bootsecurity.domain.Transaction;
import com.reed.handson.bootsecurity.domain.User;
import com.reed.handson.bootsecurity.domain.UserRole;
import com.reed.handson.bootsecurity.service.ReportingService;
import com.reed.handson.bootsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reporting")
public class ReportingController extends SecuredController {

    private final ReportingService reportingService;

    @Autowired
    public ReportingController(UserService userService, ReportingService reportingService) {
        super(userService);
        this.reportingService = reportingService;
    }

    @GetMapping(value = "/all")
    public Iterable<Transaction> report(@RequestParam(required = false) String email) {
        User user = getUser();
        if (user.getRole() == UserRole.STAFF) {
            return reportingService.findTransactionsByEmail(email == null ? user.getEmail() : email);
        } else {
            return reportingService.findTransactionsByEmail(user.getEmail());
        }
    }

    @GetMapping(value = "/unpaid")
    public Iterable<Transaction> unpaid() {
        return reportingService.findAllUnPaid();
    }

}
