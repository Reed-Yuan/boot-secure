package com.reed.handson.bootsecurity.service;

import com.reed.handson.bootsecurity.domain.User;
import com.reed.handson.bootsecurity.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {

    private UserRepository userRepository = Mockito.mock(UserRepository.class);

    private UserService userService = new UserServiceImpl(userRepository);

    @BeforeEach
    void setMockOutput() {
        when(userRepository.findByEmailIgnoreCase("mock@boot.com")).thenReturn(User.builder().email("mock@boot.com").name("mockUser").build());
    }

    @DisplayName("Test Mock UserService and UserRepository")
    @Test
    void testFindByEmail() {
        User found = userService.findByEmail("mock@boot.com");
        assertThat(found.getName()).isEqualTo("mockUser");
    }

    // Above is a demonstration of how service is tested with Spring Boot, more test code and cases may follow.
}