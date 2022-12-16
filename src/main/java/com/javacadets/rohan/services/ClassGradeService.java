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

import java.util.ArrayList;
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
        Map<String, Object> s = new LinkedHashMap<>();
        for(Student student: students) {
            Map<String, Object> record = new LinkedHashMap<>();
            List<Integer> mQuizRecord = new ArrayList<>();
            for (QuizRecord quizRecord: student.getQuizRecords()) {
               mQuizRecord.add(quizRecord.getScore());

            }
            List<Integer> mExerciseRecord = new ArrayList<>();
            for (ExerciseRecord exerciseRecord: student.getExerciseRecords()) {
                mExerciseRecord.add(exerciseRecord.getScore());

            }
            List<Integer> mProjectRecord = new ArrayList<>();
            for (ProjectRecord projectRecord: student.getProjectRecords()) {

                mProjectRecord.add(projectRecord.getScore());
            }
            record.put("quizzes", mQuizRecord);
            record.put("exercises", mExerciseRecord);
            record.put("project", mProjectRecord);

            s.put(student.getEmail(),record);
        }
        Map <String, Object> stud = new LinkedHashMap<>();
        stud.put("students",s);
        return stud;
    }

    public Map<String, Object> getStudentClassGradeSheet(String code, int batch) throws ClassNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Classs classs = this.classRepository.findActiveClass(code, batch).orElseThrow(() -> new ClassNotFoundException(code, batch));
        Student student = this.studentRepository.findByEmail(auth.getName()).orElseThrow(() -> new UserNotFoundException(auth.getName()));


        Map<String, Object> record = new LinkedHashMap<>();
        List<Integer> mQuizRecord = new ArrayList<>();
        for (QuizRecord quizRecord : student.getQuizRecords()) {
            mQuizRecord.add(quizRecord.getScore());

        }
        List<Integer> mExerciseRecord = new ArrayList<>();
        for (ExerciseRecord exerciseRecord : student.getExerciseRecords()) {
            mExerciseRecord.add(exerciseRecord.getScore());

        }
        List<Integer> mProjectRecord = new ArrayList<>();
        for (ProjectRecord projectRecord : student.getProjectRecords()) {

            mProjectRecord.add(projectRecord.getScore());
        }
        record.put("quizzes", mQuizRecord);
        record.put("exercises", mExerciseRecord);
        record.put("project", mProjectRecord);


        return record;
    }
}
