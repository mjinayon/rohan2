package com.javacadets.rohan.controllers;

import com.javacadets.rohan.services.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class ClassController {
    @Autowired
    private ClassService classService;

    @PostMapping(value = "/classes")
    public ResponseEntity<Object> addClass(@RequestBody Map<String, Object> request) {
        try {
            return new ResponseEntity<>(this.classService.saveClass(request), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(String.format("Unable to create class: %s", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(value = "/classes")
    public ResponseEntity<Object> findClasses(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        try {
            return new ResponseEntity<>(this.classService.getClasses(page, size), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(String.format("Unable to fetch classes: %s", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/classes/search")
    public ResponseEntity<Object> findClassesByKey(@RequestParam(defaultValue = "") String key,@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        try {
            return new ResponseEntity<>(this.classService.getClassesByKey(key, page, size), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(String.format("Unable to search a classes: %s", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/courses/{code}/classes/{batch}")
    public ResponseEntity<Object> findClass(@PathVariable String code, @PathVariable int batch) {
        try {
            return new ResponseEntity<>(this.classService.getClass(code,batch), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(String.format("Unable to find class: %s", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
