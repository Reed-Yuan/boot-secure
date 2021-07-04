package com.reed.handson.bootsecurity.controller;

import com.reed.handson.bootsecurity.domain.User;
import com.reed.handson.bootsecurity.service.UserService;
import com.reed.handson.bootsecurity.validation.ValidationError;
import com.reed.handson.bootsecurity.validation.ValidationErrorBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api")
public class WebController {

    private final UserService userService;

    @Autowired
    public WebController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public ResponseEntity<Iterable<User>> getUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping(value = "/user")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().
                    body(ValidationErrorBuilder.fromBindingErrors(errors));
        }
        User result = userService.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().
                path("/{id}")
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ValidationError handleException(Exception exception) {
        return new ValidationError(exception.getMessage());
    }
}