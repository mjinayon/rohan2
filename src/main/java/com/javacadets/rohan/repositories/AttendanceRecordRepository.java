package com.javacadets.rohan.repositories;

import com.javacadets.rohan.entities.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Long> {

}
