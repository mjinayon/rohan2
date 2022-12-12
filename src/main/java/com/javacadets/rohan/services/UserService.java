package com.javacadets.rohan.services;

import com.javacadets.rohan.constants.RohanRoles;
import com.javacadets.rohan.constants.RohanStatus;
import com.javacadets.rohan.email.EmailDetails;
import com.javacadets.rohan.email.EmailService;
import com.javacadets.rohan.entities.SME;
import com.javacadets.rohan.entities.Student;
import com.javacadets.rohan.entities.User;
import com.javacadets.rohan.exceptions.InvalidEmailFormatException;
import com.javacadets.rohan.exceptions.InvalidRequestBodyException;
import com.javacadets.rohan.exceptions.InvalidRoleException;
import com.javacadets.rohan.exceptions.UserNotFoundException;
import com.javacadets.rohan.helpers.EmailValidator;
import com.javacadets.rohan.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    public Map<String, Object> saveUser(Map<String, String> request) throws InvalidRoleException, InvalidRequestBodyException, InvalidEmailFormatException {

        if (request.get("email") == null || request.get("first_name") == null || request.get("last_name") == null || request.get("role") == null) {
            throw new InvalidRequestBodyException(request);
        }

//        if (!EmailValidator.validateEmail(request.get("email"))) {
//            throw new InvalidEmailFormatException(request.get("email"));
//        }

        User user = null;

        if (request.get("role").equals(RohanRoles.SME)) {
            user = new SME(request.get("email"), request.get("first_name"), request.get("last_name"));
        } else if (request.get("role").equals(RohanRoles.STUDENT)) {
            user = new Student(request.get("email"), request.get("first_name"), request.get("last_name"));
        } else {
            throw new InvalidRoleException(request.get("role"));
        }

        User savedUser = this.userRepository.save(user);

        new Thread(() -> {
            EmailDetails emailDetails = new EmailDetails(savedUser.getEmail(), "Your temporary password: " + savedUser.getTemporaryPassword(), "Rohan: Generated Temporary Password");
            System.out.println(this.emailService.sendSimpleMail(emailDetails));
            System.out.println(savedUser.getTemporaryPassword());
        }).start();

        return mapUser(savedUser, List.of("email", "first_name", "last_name", "role", "password"));
    }

    public Map<String, Object> updateUserStatus(String email) {
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        if (user.getStatus().equals(RohanStatus.DEACTIVATED)) {
            throw new UserNotFoundException(email);
        }

        user.setStatus(RohanStatus.DEACTIVATED);
        user = this.userRepository.save(user);
        return mapUser(user, List.of("email", "first_name", "last_name", "role", "status"));
    }

    public Map<String, Object> getUsers(String role, int page, int size) {
        List<Map<String, Object>> data = mapUsers(this.userRepository.findAllActiveUsersByRole(role, PageRequest.of(page-1, size)).getContent(), List.of("email", "first_name", "last_name", "role"));
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("data", data);
        response.put("page", page);
        response.put("size", size);
        return response;
    }

    public Map<String, Object> getUsersByKey(String key, int page, int size) {
        List<Map<String, Object>> data = mapUsers(this.userRepository.findAllActiveUsersByKey(key, PageRequest.of(page-1, size)).getContent(), List.of("email", "first_name", "last_name", "role"));
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("data", data);
        response.put("page", page);
        response.put("size", size);
        return response;
    }

    public Map<String, Object> getUser(String email) {
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        return mapUser(user, List.of("email", "first_name", "last_name", "role", "status"));
    }

    public static List<Map<String, Object>> mapUsers(List<? extends User> users, List<String> keys) {
        List<Map<String, Object>> mUsers = new ArrayList<>();
        for (User user: users) {
            mUsers.add(mapUser(user, keys));
        }
        return mUsers;
    }

    public static Map<String, Object> mapUser(User user, List<String> keys) {
        Map<String, Object> mUser = new LinkedHashMap<>();
        if (keys.contains("email")) {
            mUser.put("email", user.getEmail());
        }
        if (keys.contains("first_name")) {
            mUser.put("first_name", user.getFirstName());
        }
        if (keys.contains("last_name")) {
            mUser.put("last_name", user.getLastName());
        }
        if (keys.contains("role")) {
            mUser.put("role", user.getRole());
        }
        if (keys.contains("status")) {
            mUser.put("status", user.getStatus());
        }
        if (keys.contains("password")) {
            mUser.put("password", user.getPassword());
        }
        return mUser;
    }
}
