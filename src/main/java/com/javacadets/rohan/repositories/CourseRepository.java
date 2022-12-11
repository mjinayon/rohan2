package com.javacadets.rohan.repositories;

import com.javacadets.rohan.entities.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByCode(String code);
    @Query("select c from Course c where c.status='activated' and c.code=?1")
    Optional<Course> findActiveCourseByCode(String code);
    @Query("select c from Course c where c.status='activated'")
    Page<Course> findAllActiveCourses(Pageable pageable);
    @Query("select c from Course c where concat(c.code, c.title, c.description) like %?1%")
    Page<Course> findAllCoursesByKey(String key, Pageable pageable);
}
