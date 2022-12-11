package com.javacadets.rohan.controllers;

import com.javacadets.rohan.services.CourseClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
public class CourseClassController {
    @Autowired
    private CourseClassService courseClassService;

    @PostMapping(value = "/courses/{code}/classes/{batch}/enroll")
    public ResponseEntity<Object> enroll(@RequestBody Map<String, Object> request, @PathVariable String code, @PathVariable int batch, boolean enroll) {
        try {
            return new ResponseEntity<>(this.courseClassService.enrollOrUnenroll(request,code,batch, true), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>("Unable to enroll student", HttpStatus.BAD_REQUEST);
    }
    @PostMapping(value = "/courses/{code}/classes/{batch}/unenroll")
    public ResponseEntity<Object> unenroll(@RequestBody Map<String, Object> request, @PathVariable String code, @PathVariable int batch, boolean enroll) {
        try {
            return new ResponseEntity<>(this.courseClassService.enrollOrUnenroll(request,code,batch, false), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>("Unable to enroll student", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/courses/{code}/classes/{batch}/deactivate")
    public ResponseEntity<Object>deactivateClass(@PathVariable String code, @PathVariable int batch) {
        try {
            return new ResponseEntity<>(this.courseClassService.deactivate(code,batch),HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>("Unable to deactivate class", HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/courses/{code}/classes/{batch}/students")
    public ResponseEntity<Object> getStudents(@PathVariable String code, @PathVariable int batch, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        try {
            return new ResponseEntity<>(this.courseClassService.getStudents(code,batch,page,size), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>("Unable to retrieve class", HttpStatus.BAD_REQUEST);
    }
}
