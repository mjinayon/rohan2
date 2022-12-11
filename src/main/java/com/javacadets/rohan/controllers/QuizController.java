package com.javacadets.rohan.controllers;

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

    @PostMapping(value = "/classes/{code}/{batch}/quizzes")
    public ResponseEntity<Object> addQuiz(@RequestBody Map<String, Object> request, @PathVariable String code, @PathVariable int batch) {
        try {
            return new ResponseEntity<>(this.quizService.saveQuiz(request, code, batch), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Unable to add quiz: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
