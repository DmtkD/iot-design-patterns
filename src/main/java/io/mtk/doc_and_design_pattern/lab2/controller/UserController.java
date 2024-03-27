package io.mtk.doc_and_design_pattern.lab2.controller;

import io.mtk.doc_and_design_pattern.lab2.model.User;
import io.mtk.doc_and_design_pattern.lab2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Integer userId) {
        User user = userService.findById(userId);
        return new ResponseEntity<>(User.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .build(), HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        users.forEach((user) -> {
            User.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .surname(user.getSurname())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .build();
        });

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping(value = "")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User newUser = userService.create(user);
        return new ResponseEntity<>(User.builder()
                .id(newUser.getId())
                .name(newUser.getName())
                .surname(newUser.getSurname())
                .username(newUser.getUsername())
                .email(newUser.getEmail())
                .password(newUser.getPassword())
                .build(), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{userId}")
    public ResponseEntity<?> updateUser(@RequestBody User uUser, @PathVariable("userId") Integer userId) {
        userService.update(userId, uUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Integer userId) {
        userService.delete(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}