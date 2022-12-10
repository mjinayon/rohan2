package com.javacadets.rohan.services;

import com.javacadets.rohan.constants.RohanStatus;
import com.javacadets.rohan.entities.Course;
import com.javacadets.rohan.exceptions.CourseNotFoundException;
import com.javacadets.rohan.exceptions.InvalidRequestBodyException;
import com.javacadets.rohan.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public Map<String, Object> saveCourse(Map<String, String> request) throws InvalidRequestBodyException {
        if (request.get("code") == null || request.get("title") == null ||  request.get("description") == null) {
            throw new InvalidRequestBodyException(request);
        }
        Course course = this.courseRepository.save(new Course(request.get("code"), request.get("title"), request.get("description")));
        return mapCourse(course, List.of("id", "code", "title", "description"));
    }

    public Map<String, Object> updateCourseStatus(String code) throws CourseNotFoundException {
        Course course = this.courseRepository.findByCode(code).orElseThrow(() -> new CourseNotFoundException(code));
        course.setStatus(RohanStatus.DEACTIVATED);
        return mapCourse(this.courseRepository.save(course), List.of("id", "code", "title", "description", "status"));
    }

    public Map<String, Object> getCourses(int page, int size) {
        List<Map<String, Object>> data = mapCourses(this.courseRepository.findAllActiveCourses(PageRequest.of(page-1, size)).getContent(), List.of("id", "code", "title", "description"));
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("data", data);
        response.put("page", page);
        response.put("size", size);
        return response;
    }

    public Map<String, Object> getCoursesByKey(String key, int page, int size) {
        List<Map<String, Object>> data = mapCourses(this.courseRepository.findAllCoursesByKey(key, PageRequest.of(page-1, size)).getContent(), List.of("id", "code", "title", "description"));
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("data", data);
        response.put("page", page);
        response.put("size", size);
        return response;
    }

    public Map<String, Object> getCourse(String code) throws CourseNotFoundException {
        Course course = this.courseRepository.findByCode(code).orElseThrow(() -> new CourseNotFoundException(code));
        return mapCourse(course, List.of("id", "code", "title", "description", "status"));
    }

    public static List<Map<String, Object>> mapCourses(List<Course> courses, List<String> keys) {
        List<Map<String, Object>> data = new ArrayList<>();
        for(Course course: courses) {
            data.add(mapCourse(course, keys));
        }
        return data;
    }

    public static Map<String, Object> mapCourse(Course course, List<String> keys) {
        Map<String, Object> cMap = new LinkedHashMap<>();
        if (keys.contains("id")) {
            cMap.put("id", course.getCourseId());
        }
        if (keys.contains("code")) {
            cMap.put("code", course.getCode());
        }
        if (keys.contains("title")) {
            cMap.put("title", course.getTitle());
        }
        if (keys.contains("description")) {
            cMap.put("description", course.getDescription());
        }
        if (keys.contains("status")) {
            cMap.put("status", course.getStatus());
        }
        return cMap;
    }
}
