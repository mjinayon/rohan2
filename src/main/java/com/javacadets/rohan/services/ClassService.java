package com.javacadets.rohan.services;

import com.javacadets.rohan.entities.Classs;
import com.javacadets.rohan.entities.Course;
import com.javacadets.rohan.entities.SME;
import com.javacadets.rohan.entities.User;
import com.javacadets.rohan.exceptions.CourseNotFoundException;
import com.javacadets.rohan.exceptions.InvalidEndDateException;
import com.javacadets.rohan.exceptions.InvalidGradingPercentageException;
import com.javacadets.rohan.exceptions.UserNotFoundException;
import com.javacadets.rohan.repositories.ClassRepository;
import com.javacadets.rohan.repositories.CourseRepository;
import com.javacadets.rohan.repositories.SMERepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ClassService {
    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private SMERepository smeRepository;
    @Autowired
    private CourseRepository courseRepository;

    public Map<String, Object> saveClass(Map<String, Object> request) throws InvalidGradingPercentageException, InvalidEndDateException, CourseNotFoundException, ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SME sme = this.smeRepository.findByEmail(auth.getName()).orElseThrow(() -> new UserNotFoundException(auth.getName()));
        Course course = this.courseRepository.findByActiveCode((String) request.get("course_code")).orElseThrow(() -> new CourseNotFoundException((String) request.get("course_code")));

        double sumPercentage = ((double)request.get("quiz_percentage") + (double)request.get("exercise_percentage") + (double)request.get("project_percentage") + (double)request.get("attendance_percentage"));
        if ( sumPercentage != 100) {
            throw new InvalidGradingPercentageException(sumPercentage);
        }

        Date end = new SimpleDateFormat("yyyy-MM-dd").parse((String) request.get("end_date"));
        Date start = new SimpleDateFormat("yyyy-MM-dd").parse((String) request.get("start_date"));
        if(end.before(start)) {
            throw new InvalidEndDateException(start, end);
        }

        Classs classs = new Classs(course, (double) request.get("quiz_percentage"), (double) request.get("exercise_percentage"),
                                    (double) request.get("project_percentage"), (double) request.get("attendance_percentage"), start, end, sme);
        classs = this.classRepository.save(classs);
        return mapClass(classs, List.of("course", "sme", "status"));
    }

    public Map<String, Object> getClasses(int page, int size) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName() ;
        SME sme = this.smeRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        List<Classs> classses = this.classRepository.findAllBySme(sme, PageRequest.of(page-1, size)).getContent();
        List<Map<String, Object>> data = mapClasses(classses, List.of("course", "status"));
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("data", data);
        response.put("page", page);
        response.put("size", size);
        return response;
    }

    public Map<String, Object> getClassesByKey(String key, int page, int size) {
        List<Map<String, Object>> data = mapClasses(this.classRepository.findAllClassesByKey(key, PageRequest.of(page-1, size)).getContent(), List.of("course", "batch", "quiz_percentage", "quiz_percentage","project_percentage","attendance_percentage","start_date","end_date","status","sme"));
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("data", data);
        response.put("page", page);
        response.put("size", size);
        return response;
    }

    public Map<String, Object> getClass(String code, int batch) throws ClassNotFoundException {
        Classs classs = this.classRepository.findByBatchAndCourseCode(batch,code).orElseThrow(()->new ClassNotFoundException());
        return mapClass(classs,List.of("course", "batch", "quiz_percentage", "quiz_percentage","project_percentage","attendance_percentage","start_date","end_date","status","sme"));
    }

    public List<Map<String, Object>> mapClasses(List<Classs> classses, List<String> keys) {
        List<Map<String, Object>> data = new ArrayList<>();
        for (Classs classs: classses) {
            data.add(mapClass(classs, keys));
        }
        return data;
    }

    public static Map<String, Object> mapClass(Classs classs, List<String> keys) {
        Map<String, Object> mClass = new LinkedHashMap<>();
        if (keys.contains("course")) {
            mClass.put("course", CourseService.mapCourse(classs.getCourse(), List.of("code", "title", "description", "status")));
        }
        mClass.put("batch", classs.getBatch());
        mClass.put("quiz_percentage", classs.getQuizPercentage());
        mClass.put("exercise_percentage", classs.getExercisePercentage());
        mClass.put("attendance_percentage", classs.getAttendancePercentage());
        mClass.put("project_percentage", classs.getProjectPercentage());
        mClass.put("start_date", classs.getStartDate());
        mClass.put("end_date", classs.getEndDate());
        if (keys.contains("status")) {
            mClass.put("status", classs.getStatus());
        }
        if (keys.contains("sme")) {
            mClass.put("sme", UserService.mapUser(classs.getSme(), List.of("email", "first_name", "last_name", "role", "status")));
        }
        if (keys.contains("students")) {
            mClass.put("students", UserService.mapUsers(classs.getStudents().stream().toList(), List.of("email", "first_name", "last_name", "role", "status")));
        }
        return mClass;
    }
}