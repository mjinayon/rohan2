package com.javacadets.rohan.services;

import com.javacadets.rohan.constants.RohanStatus;
import com.javacadets.rohan.entities.Classs;
import com.javacadets.rohan.entities.Student;
import com.javacadets.rohan.exceptions.ClassNotFoundException;
import com.javacadets.rohan.exceptions.CourseNotFoundException;
import com.javacadets.rohan.exceptions.DeactivateClassException;
import com.javacadets.rohan.exceptions.OngoingClassException;
import com.javacadets.rohan.repositories.ClassRepository;
import com.javacadets.rohan.repositories.CourseRepository;
import com.javacadets.rohan.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class CourseClassService {
    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private StudentRepository studentsRepository;
    @Autowired
    private CourseRepository courseRepository;

    public Map<String, Object> enrollOrUnenroll(Map<String, Object> request,String code,int batch,boolean enroll) throws ClassNotFoundException {


        Student student = this.studentsRepository.findByEmail((String)request.get("email")).orElse(null);
        Classs classs = this.classRepository.findByBatchAndCourseCode(batch,code).orElseThrow(()->new ClassNotFoundException(code,batch));

        if(student.getStatus().equals(RohanStatus.DEACTIVATED)) {
            throw new ClassNotFoundException(code,batch);
        }
        if(enroll && !classs.getStudents().add(student)) {
            throw new ClassNotFoundException(code,batch);
        } else if (!enroll && !classs.getStudents().remove(student)) {
            throw new ClassNotFoundException(code,batch);
        }
        this.classRepository.save(classs);
        return ClassService.mapClass(classs, List.of("course","status", "sme","students"));
    }

    public Map<String, Object> deactivate(String code, int batch) throws CourseNotFoundException, DeactivateClassException, OngoingClassException {


        Classs classs = this.classRepository.findByBatchAndCourseCode(batch,code).orElseThrow(()->new CourseNotFoundException("Class not Found"));

        if (classs.getStatus().equals(RohanStatus.DEACTIVATED)) {
            throw new DeactivateClassException(String.format("Class '%s', batch %d is deactivated", code, batch));
        }
        if (classs.getStartDate().before(new Date()) && classs.getEndDate().after(new Date())) {

            throw new OngoingClassException(String.format("Class '%s', batch %d is ongoing", code, batch));
        }
        classs.setStatus(RohanStatus.DEACTIVATED);

        return ClassService.mapClass(this.classRepository.save(classs),List.of("course","status", "sme"));
    }

    public Map<String, Object> getStudents(String code, int batch, int page, int size) throws ClassNotFoundException {
        Classs classs = this.classRepository.findByBatchAndCourseCode(batch,code).orElseThrow(()->new ClassNotFoundException(code,batch));
        List<Student> students = this.studentsRepository.findAllByClasses(classs, PageRequest.of(page-1, size)).getContent();

        System.out.println(students);
        List<Map<String, Object>> data = new ArrayList<>();
                                    data.add(mapClass(classs, List.of("course","status","students"),students));
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("data", data);
        response.put("page", page);
        response.put("size", size);
        return response;
    }
    private Map<String, Object> mapClass(Classs classs, List<String> keys,List<Student> students) {
        Map<String, Object> mClass = new LinkedHashMap<>();
        if (keys.contains("course")) {
            mClass.put("course", CourseService.mapCourse(classs.getCourse(), List.of("code", "title", "description", "status")));
        }
        mClass.put("batch", classs.getBatch());
        mClass.put("quiz_percentage", classs.getQuizPercentage());
        mClass.put("exercise_percentage", classs.getExercisePercentage());
        mClass.put("attendance_percentage", classs.getAttendancePercentage());
        mClass.put("project_percentage", classs.getProjectPercentage());
        mClass.put("start_date", classs.getStartDate());
        mClass.put("end_date", classs.getEndDate());
        if (keys.contains("status")) {
            mClass.put("status", classs.getStatus());
        }
        if (keys.contains("sme")) {
            mClass.put("sme", UserService.mapUser(classs.getSme(), List.of("email", "first_name", "last_name", "role", "status")));
        }
        if (keys.contains("students")) {
            mClass.put("students", UserService.mapUsers(students, List.of("email", "first_name", "last_name", "role", "status")));
        }
        return mClass;
    }
}
