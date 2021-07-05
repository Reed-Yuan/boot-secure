package com.reed.handson.bootsecurity.repository;

import com.reed.handson.bootsecurity.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String> {
    public User findByEmailIgnoreCase(@Param("email") String email);
}
