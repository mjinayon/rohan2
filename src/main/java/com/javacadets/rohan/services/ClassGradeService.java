package com.javacadets.rohan.services;

import com.javacadets.rohan.entities.*;
import com.javacadets.rohan.exceptions.ClassNotFoundException;
import com.javacadets.rohan.repositories.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClassGradeService {
    @Autowired
    private ClassRepository classRepository;

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
}
