package com.reed.handson.bootsecurity.service;

import com.reed.handson.bootsecurity.domain.User;

public interface UserService {

    User findByEmail(String email);

    User save(User user);

    Iterable<User> findAll();
}
