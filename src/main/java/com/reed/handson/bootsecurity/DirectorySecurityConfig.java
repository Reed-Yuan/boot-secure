package com.reed.handson.bootsecurity;

import com.reed.handson.bootsecurity.repository.UserRepository;
import com.reed.handson.bootsecurity.service.DirectoryUserDetailsService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class DirectorySecurityConfig extends WebSecurityConfigurerAdapter {
    private UserRepository userRepository;

    public DirectorySecurityConfig(UserRepository personRepository) {
        this.userRepository = personRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/**").hasRole("ADMIN")
                .and()
                .httpBasic();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(
                new DirectoryUserDetailsService(this.userRepository));
    }
}
