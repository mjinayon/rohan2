package com.javacadets.rohan.entities;

import com.javacadets.rohan.helpers.PasswordGenerator;
import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private String firstName;
    private String lastName;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return userId == user.userId && email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, email);
    }
}
