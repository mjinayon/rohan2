package com.javacadets.rohan.repositories;

import com.javacadets.rohan.entities.Classs;
import com.javacadets.rohan.entities.SME;
import com.javacadets.rohan.entities.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClassRepository extends JpaRepository<Classs, Long> {

    @Query("select c from Classs c where concat(c.course.code, c.course.title, c.course.description,c.startDate,c.endDate) like %?1%")
    Page<Classs> findAllClassesByKey(String key, Pageable pageable);

    Page<Classs> findAllBySme(SME sme, Pageable pageable);

    Page<Classs> findAllByStudents(Student student, Pageable pageable);

    @Query("select c from Classs c where c.course.code=?1 and c.batch=?2 and c.status='activated'")
    Optional<Classs> findActiveClass(String code, int batch);

    Optional<Classs> findByBatchAndCourseCode(int batch, String code);
}
