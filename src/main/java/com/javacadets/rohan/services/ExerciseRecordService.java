package com.javacadets.rohan.services;

import com.javacadets.rohan.entities.*;
import com.javacadets.rohan.exceptions.*;
import com.javacadets.rohan.repositories.ExerciseRecordRepository;
import com.javacadets.rohan.repositories.ExerciseRepository;
import com.javacadets.rohan.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
@Service
public class ExerciseRecordService {

    @Autowired
    private ExerciseRecordRepository exerciseRecordRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ExerciseRepository exerciseRepository;

    public Map<String, Object> saveExerciseRecord(Map<String, Object> request,long exerciseId, String email) throws ExerciseNotFoundException, InvalidScoreRangeException, StudentNotEnrolledException {
        Student student = this.studentRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        Exercise exercise = this.exerciseRepository.findById(exerciseId).orElseThrow(() -> new ExerciseNotFoundException(exerciseId));

        int score = (int) request.get("score");

        if (!exercise.getClasss().getStudents().contains(student)) {
            throw new StudentNotEnrolledException(student);
        }

        if (exercise.isDeleted()) {
            throw  new ExerciseNotFoundException(exerciseId);
        }
        if (!(score> exercise.getMinScore()) || !(score < exercise.getMaxScore())) {
            throw new InvalidScoreRangeException(exercise.getMinScore(), exercise.getMaxScore(), score);
        }
        ExerciseRecord exerciseRecord = this.exerciseRecordRepository.save(new ExerciseRecord(student, exercise, score));
        return mapExerciseRecord(exerciseRecord);
    }

    public static Map<String, Object> mapExerciseRecord(ExerciseRecord exerciseRecord) {
        Map<String, Object> mExerciseRecord = new LinkedHashMap<>();
        mExerciseRecord.put("exercise_id", exerciseRecord.getExercise().getExerciseId());
        mExerciseRecord.put("email", exerciseRecord.getStudent().getEmail());
        mExerciseRecord.put("score", exerciseRecord.getScore());
        return mExerciseRecord;
    }
}
