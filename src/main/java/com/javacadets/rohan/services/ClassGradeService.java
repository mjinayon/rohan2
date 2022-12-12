package com.javacadets.rohan.services;

import com.javacadets.rohan.entities.*;
import com.javacadets.rohan.exceptions.ClassNotFoundException;
import com.javacadets.rohan.exceptions.UserNotFoundException;
import com.javacadets.rohan.repositories.ClassRepository;
import com.javacadets.rohan.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClassGradeService {
    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private StudentRepository studentRepository;

    public Map<String, Object> getClassGradeSheet(String code, int batch) throws ClassNotFoundException {
        Classs classs = this.classRepository.findActiveClass(code, batch).orElseThrow(() -> new ClassNotFoundException(code, batch));
        List<Student> students = classs.getStudents().stream().toList();
        Map<String, Object> studentsRecord = new LinkedHashMap<>();
        for(Student student: students) {
            Map<String, Object> record = new LinkedHashMap<>();
            Map<String, Object> mQuizRecord = new LinkedHashMap<>();
            for (QuizRecord quizRecord: student.getQuizRecords()) {
                mQuizRecord.put(quizRecord.getQuiz().getTitle(), quizRecord.getScore());
            }
            Map<String, Object> mExerciseRecord = new LinkedHashMap<>();
            for (ExerciseRecord exerciseRecord: student.getExerciseRecords()) {
                mExerciseRecord.put(exerciseRecord.getExercise().getTitle(), exerciseRecord.getScore());
            }
            Map<String, Object> mProjectRecord = new LinkedHashMap<>();
            for (ProjectRecord projectRecord: student.getProjectRecords()) {
                mProjectRecord.put(projectRecord.getProject().getProjectId()+"", projectRecord.getScore());
            }
            record.put("quizzes", mQuizRecord);
            record.put("exercises", mExerciseRecord);
            record.put("project", mProjectRecord);
            studentsRecord.put(student.getEmail(), record);
        }
        return studentsRecord;
    }

    public Map<String, Object> getStudentClassGradeSheet(String code, int batch) throws ClassNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Classs classs = this.classRepository.findActiveClass(code, batch).orElseThrow(() -> new ClassNotFoundException(code, batch));
        Student student = this.studentRepository.findByEmail(auth.getName()).orElseThrow(()-> new UserNotFoundException(auth.getName()));

        Map<String, Object> record = new LinkedHashMap<>();
        Map<String, Object> mQuizRecord = new LinkedHashMap<>();
        for (QuizRecord quizRecord: student.getQuizRecords()) {
            if (quizRecord.getQuiz().getClasss().equals(classs)) {
                mQuizRecord.put(quizRecord.getQuiz().getTitle(), quizRecord.getScore());
            }
        }
        Map<String, Object> mExerciseRecord = new LinkedHashMap<>();
        for (ExerciseRecord exerciseRecord: student.getExerciseRecords()) {
            if (exerciseRecord.getExercise().getClasss().equals(classs)) {
                mExerciseRecord.put(exerciseRecord.getExercise().getTitle(), exerciseRecord.getScore());
            }
        }
        Map<String, Object> mProjectRecord = new LinkedHashMap<>();
        for (ProjectRecord projectRecord: student.getProjectRecords()) {
            if(projectRecord.getProject().getClasss().equals(classs)) {
                mProjectRecord.put(projectRecord.getProject().getProjectId()+"", projectRecord.getScore());
            }
        }
        record.put("quizzes", mQuizRecord);
        record.put("exercises", mExerciseRecord);
        record.put("project", mProjectRecord);

        return record;

    }
}
