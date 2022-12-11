package com.javacadets.rohan.repositories;

import com.javacadets.rohan.entities.Classs;
import com.javacadets.rohan.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<Project> findByClasss(Classs classs);
}
