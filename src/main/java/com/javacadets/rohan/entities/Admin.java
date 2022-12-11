package com.javacadets.rohan.entities;

import com.javacadets.rohan.constants.RohanRoles;
import jakarta.persistence.Entity;

@Entity
public class Admin extends User{
    private String role;

    public Admin() {}

    public Admin(String email, String firstName, String lastName) {
        super(email, firstName, lastName);
        this.role = RohanRoles.ADMIN;
    }

    @Override
    public String getRole() {
        return role;
    }

    @Override
    public String getStatus() {
        return null;
    }

    @Override
    public void setStatus(String status) {

    }

    @Override
    public String toString() {
        return "ID: " + this.getUserId();
    }
}
