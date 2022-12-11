package com.javacadets.rohan.services;

import com.javacadets.rohan.entities.Classs;
import com.javacadets.rohan.entities.Course;
import com.javacadets.rohan.entities.Exercise;
import com.javacadets.rohan.entities.Quiz;
import com.javacadets.rohan.exceptions.*;
import com.javacadets.rohan.exceptions.ClassNotFoundException;
import com.javacadets.rohan.repositories.ClassRepository;
import com.javacadets.rohan.repositories.CourseRepository;
import com.javacadets.rohan.repositories.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.javacadets.rohan.services.QuizService.mapQuiz;

@Service
public class ExerciseService {
    @Autowired
    private ExerciseRepository exerciseRepository;
   @Autowired
    private ClassRepository classRepository;

    public Map<String, Object> saveExercise(Map<String, Object> request) throws InvalidRequestBodyException, ClassNotFoundException, ParseException {

        if (request.get("title") == null || request.get("max_score") == null || request.get("min_score") == null || request.get("date") == null) {
            throw new InvalidRequestBodyException(request);
        }
        int batch = (int) request.get("batch");
        String code = (String) request.get("code");

        String title = (String) request.get("title");
        int maxScore = (int) request.get("max_score");
        int minScore = (int) request.get("min_score");
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse((String) request.get("date"));

        Classs classs = this.classRepository.findByBatchAndCourseCode(batch, code).orElseThrow(() -> new ClassNotFoundException(code, batch));
        Exercise exercise = this.exerciseRepository.save(new Exercise(title, minScore, maxScore, date, classs));
        return mapExercise(exercise);
    }

    public Map<String, Object> deleteExercise(long exerciseId) throws QuizNotFoundException, QuizRecordNotEmptyException {
        Exercise exercise = this.exerciseRepository.findById(exerciseId).orElseThrow(() -> new QuizNotFoundException(exerciseId));
        if (!exercise.getExerciseRecords().isEmpty()) {
            throw new QuizRecordNotEmptyException(exerciseId);
        }
        exercise = this.exerciseRepository.save(exercise.delete());
        return mapExercise(exercise);
    }

    public static Map<String, Object> mapExercise(Exercise exercise) {
        Map<String, Object> mExercise = new LinkedHashMap<>();
        mExercise.put("id", exercise.getExerciseId());
        mExercise.put("title", exercise.getTitle());
        mExercise.put("min_score", exercise.getMinScore());
        mExercise.put("max_score", exercise.getMaxScore());
        mExercise.put("date", exercise.getDate());
        mExercise.put("code", exercise.getClasss().getCourse().getCode());
        mExercise.put("batch", exercise.getClasss().getBatch());
        return mExercise;
    }
}
