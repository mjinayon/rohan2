package com.javacadets.rohan.services;


import com.javacadets.rohan.constants.RohanStatus;
import com.javacadets.rohan.entities.AttendanceRecord;
import com.javacadets.rohan.entities.Classs;
import com.javacadets.rohan.entities.Student;
import com.javacadets.rohan.exceptions.*;
import com.javacadets.rohan.exceptions.ClassNotFoundException;
import com.javacadets.rohan.repositories.AttendanceRecordRepository;
import com.javacadets.rohan.repositories.ClassRepository;
import com.javacadets.rohan.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class AttendanceRecordService {

    @Autowired
    private AttendanceRecordRepository attendanceRecordRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ClassRepository classRepository;

    public Map<String, Object> saveAttendance(Map<String, Object> request, String email) throws ClassNotFoundException, ClassNotYetStartedException, DeactivateClassException, InvalidRequestBodyException, StudentNotEnrolledException {

        if (request.get("code") == null || request.get("batch") == null) {
            throw new InvalidRequestBodyException(request);
        }
        int batch = (int) request.get("batch");
        String code = (String) request.get("code");
        Student student = this.studentRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        Classs classs = this.classRepository.findByBatchAndCourseCode(batch,code).orElseThrow(()->new ClassNotFoundException(code,batch));

        if(!(classs.getStudents().contains(student))) {
            throw new StudentNotEnrolledException(student);
        }
        if(classs.getStatus().equals(RohanStatus.DEACTIVATED)) {
            throw new DeactivateClassException("Cannot add attendance, class is deactivated");
        }
        if(student.getStatus().equals(RohanStatus.DEACTIVATED)) {
            throw new UserNotFoundException(student.getEmail());
        }
        AttendanceRecord attendanceRecord = this.attendanceRecordRepository.save(new AttendanceRecord(classs,student));
        this.attendanceRecordRepository.save(attendanceRecord.present());

        return mapAttendance(attendanceRecord);
    }

    public static Map<String, Object> mapAttendance(AttendanceRecord attendanceRecord) {
        Map<String, Object> mAttendanceRec = new LinkedHashMap<>();
        mAttendanceRec.put("class", "Course code: "+attendanceRecord.getClasss().getCourse().getCode()+" Batch: "+attendanceRecord.getClasss().getBatch());
        mAttendanceRec.put("email", attendanceRecord.getStudent().getEmail());
        mAttendanceRec.put("date", attendanceRecord.getDate());
        if (attendanceRecord.isPresent()) {
            mAttendanceRec.put("attended", "Present");
        } else {
            mAttendanceRec.put("attended", "Absent");
        }
        return mAttendanceRec;
    }
}
