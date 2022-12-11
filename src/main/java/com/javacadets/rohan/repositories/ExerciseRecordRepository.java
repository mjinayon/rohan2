package com.javacadets.rohan.repositories;

import com.javacadets.rohan.entities.ExerciseRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRecordRepository extends JpaRepository<ExerciseRecord, Long> {
}
