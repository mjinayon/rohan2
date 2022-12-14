package com.javacadets.rohan.controllers;

import com.javacadets.rohan.helpers.ErrorHandler;
import com.javacadets.rohan.services.ExerciseRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExerciseRecordController {
    @Autowired
    private ExerciseRecordService exerciseRecordService;

    @PostMapping(value = "/exercises/{exerciseId}/add-record/{email}/{score}")
    public ResponseEntity<Object> addQuizRecord(@PathVariable long exerciseId, @PathVariable String email, @PathVariable int score) {
        try {
            return new ResponseEntity<>(this.exerciseRecordService.saveExerciseRecord(exerciseId, email, score), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ErrorHandler.err(e), HttpStatus.BAD_REQUEST);
        }
    }
}
