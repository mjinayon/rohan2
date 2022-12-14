package com.javacadets.rohan.controllers;

import com.javacadets.rohan.helpers.ErrorHandler;
import com.javacadets.rohan.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping(value = "/courses")
    public ResponseEntity<Object> addCourse(@RequestBody Map<String, String> request) {
        try {
            return new ResponseEntity<>(this.courseService.saveCourse(request), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ErrorHandler.err(e), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/courses/{code}/deactivate")
    public ResponseEntity<Object> deactivateCourse(@PathVariable String code) {
        try {
            return new ResponseEntity<>(this.courseService.updateCourseStatus(code), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ErrorHandler.err(e), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/courses")
    public ResponseEntity<Object> findCourses(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        try {
            return new ResponseEntity<>(this.courseService.getCourses(page, size), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ErrorHandler.err(e), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/courses/search")
    public ResponseEntity<Object> findCoursesByKey(@RequestParam(defaultValue = "") String key,@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        try {
            return new ResponseEntity<>(this.courseService.getCoursesByKey(key, page, size), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ErrorHandler.err(e), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/courses/{code}")
    public ResponseEntity<Object> findCourse(@PathVariable String code) {
        try {
            return new ResponseEntity<>(this.courseService.getCourse(code), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ErrorHandler.err(e), HttpStatus.BAD_REQUEST);
        }
    }
}
