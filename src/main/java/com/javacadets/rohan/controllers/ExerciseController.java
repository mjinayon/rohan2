package com.javacadets.rohan.controllers;

import com.javacadets.rohan.services.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ExerciseController {

    @Autowired
    private ExerciseService exerciseService;

    @PostMapping(value="/exercises")
    public ResponseEntity<Object> addExercise(@RequestBody Map<String,Object> request) {
        try {
            return new ResponseEntity<>(this.exerciseService.saveExercise(request), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Unable to add exercise "+ e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping(value = "/exercises/{exerciseId}/delete")
    public ResponseEntity<Object> deleteExercise(@PathVariable long quizId) {
        try {
            return new ResponseEntity<>(this.exerciseService.deleteExercise(quizId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(String.format("Unable to delete exercise id '%d': %s", quizId, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
