package com.javacadets.rohan.repositories;

import com.javacadets.rohan.entities.ProjectRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRecordRepository extends JpaRepository<ProjectRecord, Long> {
}
