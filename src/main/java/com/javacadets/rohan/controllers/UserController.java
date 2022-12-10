package com.javacadets.rohan.controllers;

import com.javacadets.rohan.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/users")
    public ResponseEntity<Object> addUser(@RequestBody Map<String, String> request) {
        try {
            return new ResponseEntity<>(this.userService.saveUser(request), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(String.format("Unable to add user: %s", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/users/{email}/deactivate")
    public ResponseEntity<Object> deactivateUser(@PathVariable String email) {
        try {
            return new ResponseEntity<>(this.userService.updateUserStatus(email), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(String.format("Unable to add user: %s", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/users")
    public ResponseEntity<Object> findUsers(@RequestParam(defaultValue = "student") String role, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        try {
            return new ResponseEntity<>(this.userService.getUsers(role, page, size), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(String.format("Unable to fetch users: %s", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/users/search")
    public ResponseEntity<Object> findUsersByKey(@RequestParam(defaultValue = "") String key, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        try {
            return new ResponseEntity<>(this.userService.getUsersByKey(key, page, size), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(String.format("Unable to fetch users: %s", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/users/{email}")
    public ResponseEntity<Object> findUser(@PathVariable String email)  {
        try {
            return new ResponseEntity<>(this.userService.getUser(email), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(String.format("Unable to fetch user: %s", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
