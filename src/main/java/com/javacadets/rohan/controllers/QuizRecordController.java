package com.javacadets.rohan.controllers;

import com.javacadets.rohan.helpers.ErrorHandler;
import com.javacadets.rohan.services.QuizRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class QuizRecordController {
    @Autowired
    private QuizRecordService quizRecordService;

    @PostMapping(value = "/quizzes/{quizId}/add-record/{email}")
    public ResponseEntity<Object> addQuizRecord(@RequestBody Map<String, Object> request, @PathVariable long quizId, @PathVariable String email) {
        try {
            return new ResponseEntity<>(this.quizRecordService.saveQuizRecord(request,quizId, email), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ErrorHandler.err(e), HttpStatus.BAD_REQUEST);
        }
    }
}
