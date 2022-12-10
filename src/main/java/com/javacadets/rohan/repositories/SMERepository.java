package com.javacadets.rohan.repositories;

import com.javacadets.rohan.entities.Classs;
import com.javacadets.rohan.entities.SME;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SMERepository extends JpaRepository<SME, Long> {
    Optional<SME> findByEmail(String email);

    @Query("select s.classes from SME s where s.email=?1")
    Page<Classs> findClasses(String email, Pageable pageable);
}
