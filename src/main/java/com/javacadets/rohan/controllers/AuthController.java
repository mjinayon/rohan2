package com.javacadets.rohan.controllers;

import com.javacadets.rohan.security.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtGenerator jwtGenerator;

    @PostMapping(value = "/auth/login")
    public ResponseEntity<Object> login(@RequestBody Map<String, String> request) {
        try {
            Authentication authentication = this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.get("email"),request.get("password")));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = this.jwtGenerator.generateToken(authentication);
            return new ResponseEntity<>("Authorization: Bearer " + token, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(String.format("Unable to login user: %s", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
