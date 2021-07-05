package com.reed.handson.bootsecurity;

import com.reed.handson.bootsecurity.domain.Transaction;
import com.reed.handson.bootsecurity.domain.TransactionState;
import com.reed.handson.bootsecurity.domain.User;
import com.reed.handson.bootsecurity.service.TransactionService;
import com.reed.handson.bootsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        seedData();
    }

    private void seedData() {
        User admin = userService.findByEmail("admin@boot.com");

        transactionService.save(Transaction.builder().spender(admin).amount(1D).state(TransactionState.NOT_PAID).build());
        transactionService.save(Transaction.builder().spender(admin).amount(2D).state(TransactionState.NOT_PAID).build());
        transactionService.save(Transaction.builder().spender(admin).amount(3D).state(TransactionState.NOT_PAID).build());
        transactionService.save(Transaction.builder().spender(admin).amount(4D).state(TransactionState.NOT_PAID).build());

        User staff = userService.findByEmail("staff@boot.com");
        transactionService.save(Transaction.builder().spender(staff).amount(100D).state(TransactionState.NOT_PAID).build());
        transactionService.save(Transaction.builder().spender(staff).amount(200D).state(TransactionState.NOT_PAID).build());
        transactionService.save(Transaction.builder().spender(staff).amount(300D).state(TransactionState.NOT_PAID).build());
        transactionService.save(Transaction.builder().spender(staff).amount(400D).state(TransactionState.NOT_PAID).build());

        User user = userService.findByEmail("user@boot.com");
        transactionService.save(Transaction.builder().spender(user).amount(10D).state(TransactionState.NOT_PAID).build());
        transactionService.save(Transaction.builder().spender(user).amount(20D).state(TransactionState.NOT_PAID).build());
        transactionService.save(Transaction.builder().spender(user).amount(30D).state(TransactionState.NOT_PAID).build());
        transactionService.save(Transaction.builder().spender(user).amount(40D).state(TransactionState.NOT_PAID).build());
        transactionService.save(Transaction.builder().spender(user).amount(50D).state(TransactionState.NOT_PAID).build());
    }
}
