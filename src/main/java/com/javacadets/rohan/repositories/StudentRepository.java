package com.javacadets.rohan.repositories;

import com.javacadets.rohan.entities.Classs;
import com.javacadets.rohan.entities.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("select s from Student s where s.email=?1 and s.status='activated'")
    Optional<Student> findByEmail(String email);

    Page<Student> findAllByClasses(Classs classs, Pageable pageable);
}
