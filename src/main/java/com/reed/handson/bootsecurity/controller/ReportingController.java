package com.reed.handson.bootsecurity.controller;

import com.reed.handson.bootsecurity.domain.Transaction;
import com.reed.handson.bootsecurity.domain.User;
import com.reed.handson.bootsecurity.domain.UserRole;
import com.reed.handson.bootsecurity.service.ReportingService;
import com.reed.handson.bootsecurity.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
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
    @Operation(summary = "List transactions of a particular user, a STAFF can list transactions of another user specified by 'email' parameter; Non-STAFF gets all transactions of his/her own")
    public Page<Transaction> report(@RequestParam(required = false) String email, Pageable pageable) {
        return listTransactions(email, false, pageable);
    }

    @GetMapping(value = "/unpaid")
    @Operation(summary = "List NOT_PAID transactions of a particular user, a STAFF can list NOT_PAID transactions of another user specified by 'email' parameter; Non-STAFF gets all NOT_PAID transactions of his/her own")
    public Page<Transaction> unpaid(@RequestParam(required = false) String email, Pageable pageable) {
        return listTransactions(email, true, pageable);
    }

    private Page<Transaction> listTransactions(final String email, final boolean checkUnpaid, final Pageable pageable) {
        User user = getUser();
        if (user.getRole() == UserRole.STAFF) {
            if (checkUnpaid) {
                return reportingService.findUnPaidByEmail(email == null ? user.getEmail() : email, pageable);
            } else {
                return reportingService.findTransactionsByEmail(email == null ? user.getEmail() : email, pageable);
            }
        } else if (email != null && !email.equals(user.getEmail())) {
            throw new AccessDeniedException("Not authorized");
        } else {
            if (checkUnpaid) {
                return reportingService.findUnPaidByEmail(user.getEmail(), pageable);
            } else {
                return reportingService.findTransactionsByEmail(user.getEmail(), pageable);
            }
        }
    }

}
