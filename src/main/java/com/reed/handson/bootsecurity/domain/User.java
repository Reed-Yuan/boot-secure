package com.reed.handson.bootsecurity.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
public class User {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @NotNull
    @NotBlank
    @Size(min=2, max=10)
    private String name;

    @JsonIgnore
    private String password;

    @NotNull
    @NotBlank
    @Column(unique = true)
    @Email(message = "is not a valid Email address")
    private String email;

    private boolean enabled = true;

    @Column(insertable = true, updatable = false)
    private LocalDateTime created;

    private LocalDateTime updated;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    @Tolerate
    public User() {}

    @PrePersist
    void onCreate() {
        this.setCreated(LocalDateTime.now());
        this.setUpdated(LocalDateTime.now());
    }

    @PreUpdate
    void onUpdate() {
        this.setUpdated(LocalDateTime.now());
    }
}
