package com.javacadets.rohan.controllers;

import com.javacadets.rohan.services.QuizRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuizRecordController {
    @Autowired
    private QuizRecordService quizRecordService;

    @PostMapping(value = "/quizzes/{quizId}/add-record/{email}/{score}")
    public ResponseEntity<Object> addQuizRecord(@PathVariable long quizId, @PathVariable String email, @PathVariable int score) {
        try {
            return new ResponseEntity<>(this.quizRecordService.saveQuizRecord(quizId, email, score), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(String.format("Unable to add record: %s", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
