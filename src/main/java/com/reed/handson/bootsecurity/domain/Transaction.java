package com.reed.handson.bootsecurity.domain;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
public class Transaction {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @ManyToOne
    @JoinColumn(name="USER_ID")
    private User spender;

    private String merchant;

    @NotNull
    @Max(value=1000, message="over credit limit $1000")
    private Double amount;

    @Column(insertable = true, updatable = false)
    private LocalDateTime created;

    private LocalDateTime updated;

    @Enumerated(EnumType.STRING)
    private TransactionState state = TransactionState.NOT_PAID;

    private String description;

    @PrePersist
    void onCreate() {
        this.setCreated(LocalDateTime.now());
        this.setUpdated(LocalDateTime.now());
    }

    @PreUpdate
    void onUpdate() {
        this.setUpdated(LocalDateTime.now());
    }

    @Tolerate
    public Transaction() {}
}
