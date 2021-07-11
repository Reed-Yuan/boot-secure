package com.reed.handson.bootsecurity.controller;

import com.reed.handson.bootsecurity.domain.User;
import com.reed.handson.bootsecurity.service.UserService;
import com.reed.handson.bootsecurity.validation.ValidationErrorBuilder;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api")
public class UserController extends SecuredController {

    @Autowired
    public UserController(final UserService userService) {
        super(userService);
    }

    @GetMapping("/user")
    @Operation(summary = "List all users, available for ADMIN or STAFF")
    public ResponseEntity<Iterable<User>> getUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping(value = "/user")
    @Operation(summary = "Create new user, available for ADMIN or STAFF")
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
}
