package com.javacadets.rohan.controllers;

import com.javacadets.rohan.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {
    @Autowired
    private StudentService studentService;
    @GetMapping(value = "/my-classes")
    public ResponseEntity<Object> getClasses(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        try {
            return new ResponseEntity<>(this.studentService.getClassesOfStudent(page, size), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(String.format("Unable to fetch users: %s", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
