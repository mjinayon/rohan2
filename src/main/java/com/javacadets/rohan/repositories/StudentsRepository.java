package com.javacadets.rohan.repositories;

import com.javacadets.rohan.entities.Classs;
import com.javacadets.rohan.entities.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface StudentsRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email);

    Page<Student> findAllByClasses(Classs classs, Pageable pageable);
}
