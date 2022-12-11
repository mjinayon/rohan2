package com.javacadets.rohan.entities;

import com.javacadets.rohan.constants.RohanRoles;
import com.javacadets.rohan.constants.RohanStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.util.Set;

@Entity
public class SME extends User{
    private final String role  = RohanRoles.SME;
    private String status;

    @OneToMany(mappedBy = "sme", fetch = FetchType.LAZY)
    private Set<Classs> classes;

    public SME() {}

    public SME(String email, String firstName, String lastName) {
        super(email, firstName, lastName);
        this.status = RohanStatus.ACTIVATED;
    }

    @Override
    public String getRole() {
        return role;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ID: " + this.getUserId();
    }
}
