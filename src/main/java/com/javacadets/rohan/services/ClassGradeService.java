package com.javacadets.rohan.services;

import com.javacadets.rohan.entities.*;
import com.javacadets.rohan.exceptions.ClassNotFoundException;
import com.javacadets.rohan.exceptions.UserNotFoundException;
import com.javacadets.rohan.repositories.ClassRepository;
import com.javacadets.rohan.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public List<Map<String, Object>> getClassGradeSheet(String code, int batch) throws ClassNotFoundException {
        Classs classs = this.classRepository.findActiveClass(code, batch).orElseThrow(() -> new ClassNotFoundException(code, batch));
        List<Student> students = classs.getStudents().stream().toList();
        List<Map<String, Object>> studentsRecord = new ArrayList<>();
        for(Student student: students) {
            studentsRecord.add(mapStudentRecord(student));
        }
        return studentsRecord;
    }

    public Map<String, Object> getStudentClassGradeSheet(String code, int batch) throws ClassNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student student = this.studentRepository.findByEmail(auth.getName()).orElseThrow(() -> new UserNotFoundException(auth.getName()));
        return mapStudentRecord(student);
    }

    public static Map<String, Object> mapStudentRecord(Student student) {
        Map<String, Object> record = new LinkedHashMap<>();
        List<Map<String, Object>> mQuizRecord = new ArrayList<>();
        for (QuizRecord quizRecord : student.getQuizRecords()) {
            Map<String, Object> obj = new LinkedHashMap<>();
            obj.put("id", quizRecord.getQuiz().getQuizId());
            obj.put("title", quizRecord.getQuiz().getTitle());
            obj.put("score", quizRecord.getScore());
            mQuizRecord.add(obj);
        }
        List<Map<String, Object>> mExerciseRecord = new ArrayList<>();
        for (ExerciseRecord exerciseRecord : student.getExerciseRecords()) {
            Map<String, Object> obj = new LinkedHashMap<>();
            obj.put("id", exerciseRecord.getExercise().getExerciseId());
            obj.put("title", exerciseRecord.getExercise().getTitle());
            obj.put("score", exerciseRecord.getScore());
            mExerciseRecord.add(obj);
        }
        List<Map<String, Object>> mProjectRecord = new ArrayList<>();
        for (ProjectRecord projectRecord : student.getProjectRecords()) {
            Map<String, Object> obj = new LinkedHashMap<>();
            obj.put("id", projectRecord.getProject().getProjectId());
            obj.put("score", projectRecord.getScore());
            mProjectRecord.add(obj);
        }

        record.put("email", student.getEmail());
        record.put("quizzes", mQuizRecord);
        record.put("exercises", mExerciseRecord);
        record.put("project", mProjectRecord);

        return record;
    }
}
