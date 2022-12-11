package com.javacadets.rohan.services;

import com.javacadets.rohan.entities.Quiz;
import com.javacadets.rohan.entities.QuizRecord;
import com.javacadets.rohan.entities.Student;
import com.javacadets.rohan.entities.User;
import com.javacadets.rohan.exceptions.InvalidRequestBodyException;
import com.javacadets.rohan.exceptions.QuizNotFoundException;
import com.javacadets.rohan.exceptions.UserNotFoundException;
import com.javacadets.rohan.repositories.QuizRecordRepository;
import com.javacadets.rohan.repositories.QuizRepository;
import com.javacadets.rohan.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class QuizRecordService {
    @Autowired
    private QuizRecordRepository quizRecordRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private QuizRepository quizRepository;

    public Map<String, Object> saveQuizRecord(long quizId, String email, int score) throws InvalidRequestBodyException, QuizNotFoundException {
        Student student = this.studentRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        Quiz quiz = this.quizRepository.findById(quizId).orElseThrow(() -> new QuizNotFoundException(quizId));
        QuizRecord quizRecord = this.quizRecordRepository.save(new QuizRecord(student, quiz, score));
        return mapQuizRecord(quizRecord);
    }

    public static Map<String, Object> mapQuizRecord(QuizRecord quizRecord) {
        Map<String, Object> mQuizRecord = new LinkedHashMap<>();
        mQuizRecord.put("quiz", quizRecord.getQuiz().getQuizId());
        mQuizRecord.put("email", quizRecord.getStudent().getEmail());
        mQuizRecord.put("score", quizRecord.getScore());
        return mQuizRecord;
    }
}