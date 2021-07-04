package com.reed.handson.bootsecurity.service;

import com.reed.handson.bootsecurity.domain.User;

public interface UserService {
    User createUser(final String firstName, final String lastName, final String email);

    User save(User user);

    Iterable<User> findAll();
}
