package com.javacadets.rohan.entities;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.javacadets.rohan.helpers.PasswordGenerator;
import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@JsonFilter("filter_user")
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(value = "id")
    private long userId;
    @Column(unique = true, nullable = false)
    @JsonProperty(value = "email")
    private String email;
    @JsonProperty(value = "password")
    private String password;
    @JsonProperty(value = "first_name")
    private String firstName;
    @JsonProperty(value = "last_name")
    private String lastName;

    @JsonProperty(value = "temporary_password")
    @Transient
    private String temporaryPassword;

    public User() {}

    public User(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;

        this.temporaryPassword = PasswordGenerator.generatePassword(PasswordGenerator.DEFAULT_PASS_LENGTH);
        this.password = new BCryptPasswordEncoder().encode(this.temporaryPassword);
    }

    public abstract String getRole();
    public abstract String getStatus();

    public abstract void setStatus(String status);

    public long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getTemporaryPassword() {
        return this.temporaryPassword;
    }


}
