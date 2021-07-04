package com.reed.handson.bootsecurity.repository;

import com.reed.handson.bootsecurity.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
