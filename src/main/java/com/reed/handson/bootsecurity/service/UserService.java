package com.reed.handson.bootsecurity.service;

import com.reed.handson.bootsecurity.domain.User;

public interface UserService {

    User save(User user);

    Iterable<User> findAll();
}
