package com.reed.handson.bootsecurity;

import com.reed.handson.bootsecurity.repository.UserRepository;
import com.reed.handson.bootsecurity.service.DirectoryUserDetailsService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;

import static org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive.*;

@Configuration
@EnableScheduling
public class DirectorySecurityConfig extends WebSecurityConfigurerAdapter {

    private static final ClearSiteDataHeaderWriter.Directive[] SOURCE =
            {CACHE, COOKIES, STORAGE, EXECUTION_CONTEXTS};

    private UserRepository userRepository;

    public DirectorySecurityConfig(UserRepository personRepository) {
        this.userRepository = personRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/logout").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/api/user/**").hasAnyRole("ADMIN", "STAFF")
                .antMatchers("/transaction/**").hasAnyRole("ADMIN", "STAFF", "USER")
                .antMatchers("/reporting/**").hasAnyRole("ADMIN", "STAFF", "USER")
                .and()
                .httpBasic()
                .and()
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .addLogoutHandler(new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(SOURCE)))
                );
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(
                new DirectoryUserDetailsService(this.userRepository));
    }
}
