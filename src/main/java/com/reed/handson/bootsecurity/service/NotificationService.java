package com.reed.handson.bootsecurity.service;

import com.reed.handson.bootsecurity.domain.Transaction;
import com.reed.handson.bootsecurity.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Component
@EnableAsync
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

    @Autowired
    private TransactionService transactionService;

    @Async
    @Scheduled(fixedRate = 60000) // Every minute, could use Cron Expression
    public void scheduleFixedRateTaskAsync() throws InterruptedException {
        List<Transaction> allUnPaid = transactionService.findAllUnPaid();
        Map<User, List<Transaction>> byUser = allUnPaid.stream().collect(groupingBy(Transaction::getSpender));
        log.info("Scan database for unpaid transactions as of {}", dateFormat.format(new Date()));
        for (Map.Entry<User, List<Transaction>> each: byUser.entrySet()) {
            notifyUser(each.getKey(), each.getValue());
        }
    }

    private void notifyUser(User user, List<Transaction> unPaid) {
        // Below is mock code, could use JavaMailSender or other messaging services in reality
        log.info("Sending Email to: {}", user.getEmail());
        log.info("Message: {}",  unPaid.toString());
    }
}
