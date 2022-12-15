package com.javacadets.rohan.controllers;

import com.javacadets.rohan.helpers.ErrorHandler;
import com.javacadets.rohan.services.ExerciseRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ExerciseRecordController {
    @Autowired
    private ExerciseRecordService exerciseRecordService;

    @PostMapping(value = "/exercises/{exerciseId}/add-record/{email}")
    public ResponseEntity<Object> addQuizRecord(@RequestBody Map<String,Object> request, @PathVariable long exerciseId, @PathVariable String email) {
        try {
            return new ResponseEntity<>(this.exerciseRecordService.saveExerciseRecord(request,exerciseId, email), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ErrorHandler.err(e), HttpStatus.BAD_REQUEST);
        }
    }
}
