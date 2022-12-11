package com.javacadets.rohan.services;

import com.javacadets.rohan.entities.Classs;
import com.javacadets.rohan.entities.Quiz;
import com.javacadets.rohan.exceptions.ClassNotFoundException;
import com.javacadets.rohan.exceptions.InvalidRequestBodyException;
import com.javacadets.rohan.repositories.ClassRepository;
import com.javacadets.rohan.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuizService {
    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private ClassRepository classRepository;

    public Map<String, Object> saveQuiz(Map<String, Object> request, String code, int batch) throws InvalidRequestBodyException, ClassNotFoundException, ParseException {

        if (request.get("title") == null || request.get("max_score") == null || request.get("min_score") == null || request.get("date") == null) {
            throw new InvalidRequestBodyException(request);
        }

        String title = (String) request.get("title");
        int maxScore = (int) request.get("max_score");
        int minScore = (int) request.get("min_score");
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse((String) request.get("date"));

        Classs classs = this.classRepository.findByBatchAndCourseCode(batch, code).orElseThrow(() -> new ClassNotFoundException(code, batch));
        Quiz quiz = this.quizRepository.save(new Quiz(title, minScore, maxScore, date, classs));
        return mapQuiz(quiz);
    }

    public static Map<String, Object> mapQuiz(Quiz quiz) {
        Map<String, Object> mQuiz = new LinkedHashMap<>();
        mQuiz.put("id", quiz.getQuizId());
        mQuiz.put("title", quiz.getTitle());
        mQuiz.put("min_score", quiz.getMinScore());
        mQuiz.put("max_score", quiz.getMaxScore());
        mQuiz.put("date", quiz.getDate());
        mQuiz.put("code", quiz.getClasss().getCourse().getCode());
        mQuiz.put("batch", quiz.getClasss().getBatch());
        return mQuiz;
    }
}
