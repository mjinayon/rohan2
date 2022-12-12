package com.javacadets.rohan.services;

import com.javacadets.rohan.entities.Classs;
import com.javacadets.rohan.entities.Student;
import com.javacadets.rohan.exceptions.CourseNotFoundException;
import com.javacadets.rohan.exceptions.InvalidEndDateException;
import com.javacadets.rohan.exceptions.InvalidGradingPercentageException;
import com.javacadets.rohan.exceptions.UserNotFoundException;
import com.javacadets.rohan.repositories.ClassRepository;
import com.javacadets.rohan.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.javacadets.rohan.services.ClassService.mapClasses;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ClassRepository classRepository;
    public Map<String, Object> getClassesOfStudent(int page, int size)  {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student student = this.studentRepository.findByEmail(auth.getName()).orElseThrow(() ->new UserNotFoundException(auth.getName()));
        List<Classs> classses = this.classRepository.findAllByStudents(student, PageRequest.of(page-1, size)).getContent();

        List<Map<String, Object>> data = mapClasses(classses, List.of("course", "status"));
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("data", data);
        response.put("page", page);
        response.put("size", size);
        return response;

    }
}
