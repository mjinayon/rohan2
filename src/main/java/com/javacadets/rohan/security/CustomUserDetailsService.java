package com.javacadets.rohan.security;

import com.javacadets.rohan.constants.RohanRoles;
import com.javacadets.rohan.constants.RohanStatus;
import com.javacadets.rohan.entities.Admin;
import com.javacadets.rohan.entities.User;
import com.javacadets.rohan.entities.SME;
import com.javacadets.rohan.entities.Student;
import com.javacadets.rohan.exceptions.UserNotFoundException;
import com.javacadets.rohan.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UserNotFoundException {
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        String role = "";
        if (user instanceof Admin) {
            role = RohanRoles.ADMIN;
        } else if (user instanceof SME) {
            role = RohanRoles.SME;
        } else if (user instanceof Student) {
            role = RohanRoles.STUDENT;
        }

        if ((role.equals(RohanRoles.SME) || role.equals(RohanRoles.STUDENT)) && user.getStatus().equals(RohanStatus.DEACTIVATED)) {
            throw new UserNotFoundException(email);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(), List.of(new SimpleGrantedAuthority(role)));
    }
}
