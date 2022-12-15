package com.javacadets.rohan.controllers;

import com.javacadets.rohan.helpers.ErrorHandler;
import com.javacadets.rohan.repositories.ProjectRecordRepository;
import com.javacadets.rohan.repositories.ProjectRepository;
import com.javacadets.rohan.services.ProjectRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ProjectRecordController {

    @Autowired
    private ProjectRecordService projectRecordService;

    @PostMapping(value = "/courses/{code}/classes/{batch}/projects/{email}")
    public ResponseEntity<Object> addProjectRecord(@RequestBody Map<String, Object> request, @PathVariable String code, @PathVariable int batch, @PathVariable String email) {
        try {
            return new ResponseEntity<>(this.projectRecordService.saveProjectRecord(request,code,batch, email), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ErrorHandler.err(e), HttpStatus.BAD_REQUEST);
        }
    }
}
