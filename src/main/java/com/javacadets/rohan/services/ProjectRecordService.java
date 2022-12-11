package com.javacadets.rohan.services;

import com.javacadets.rohan.entities.*;
import com.javacadets.rohan.exceptions.ClassNotFoundException;
import com.javacadets.rohan.exceptions.ProjectNotFoundException;
import com.javacadets.rohan.exceptions.QuizNotFoundException;
import com.javacadets.rohan.exceptions.UserNotFoundException;
import com.javacadets.rohan.repositories.ClassRepository;
import com.javacadets.rohan.repositories.ProjectRecordRepository;
import com.javacadets.rohan.repositories.ProjectRepository;
import com.javacadets.rohan.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class ProjectRecordService {

    @Autowired
    private ProjectRecordRepository projectRecordRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ClassRepository classRepository;

    public Map<String, Object> saveProjectRecord(String code, int batch, String email, int score) throws ProjectNotFoundException, ClassNotFoundException {
        Student student = this.studentRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        Classs classs = this.classRepository.findByBatchAndCourseCode(batch,code).orElseThrow(()->new ClassNotFoundException(code,batch));
        Project project = this.projectRepository.findByClasss(classs).orElse(null);
        ProjectRecord projectRecord = this.projectRecordRepository.save(new ProjectRecord(student, project, score));
        return mapProjectRecord(projectRecord);
    }

    public static Map<String, Object> mapProjectRecord(ProjectRecord projectRecord) {
        Map<String, Object> mProjectRecord = new LinkedHashMap<>();
        mProjectRecord.put("project", projectRecord.getProject().getClass());
        mProjectRecord.put("email", projectRecord.getStudent().getEmail());
        mProjectRecord.put("score", projectRecord.getScore());
        return mProjectRecord;
    }
}
