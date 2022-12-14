package com.javacadets.rohan.controllers;

import com.javacadets.rohan.helpers.ErrorHandler;
import com.javacadets.rohan.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class QuizController {
    @Autowired
    private QuizService quizService;

    @PostMapping(value = "/quizzes")
    public ResponseEntity<Object> addQuiz(@RequestBody Map<String, Object> request) {
        try {
            return new ResponseEntity<>(this.quizService.saveQuiz(request), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ErrorHandler.err(e), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/quizzes/{quizId}/delete")
    public ResponseEntity<Object> deleteQuiz(@PathVariable long quizId) {
        try {
            return new ResponseEntity<>(this.quizService.deleteQuiz(quizId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ErrorHandler.err(e), HttpStatus.BAD_REQUEST);
        }
    }
}
