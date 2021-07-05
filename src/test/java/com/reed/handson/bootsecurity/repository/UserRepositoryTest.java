package com.reed.handson.bootsecurity.repository;

import com.reed.handson.bootsecurity.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repository;

    @Test
    public void userDataTest() throws Exception {
        User user1 = User.builder().email("user100@boot.com").name("user100").build();
        entityManager.persist(user1);
        User found = repository.findByEmailIgnoreCase("user100@boot.com");
        assertThat(found.getId()).isNotNull();
        assertThat(found.getName()).isEqualTo(user1.getName());
    }

    // Above is a demonstration of a JPA repository, more testing code and cases should follow
}