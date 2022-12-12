package com.javacadets.rohan.controllers;

import com.javacadets.rohan.services.ClassGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RetrieveClassGradeSheet {
    @Autowired
    private ClassGradeService classGradeService;

    @GetMapping(value = "/courses/{code}/classes/{batch}/generate-grades")
    public ResponseEntity<Object> test(@PathVariable String code, @PathVariable int batch) {
        try {
            return new ResponseEntity<>(this.classGradeService.getClassGradeSheet(code, batch), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
