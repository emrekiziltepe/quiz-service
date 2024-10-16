package org.example.quiz.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.quiz.data.request.StudentRequest;
import org.example.quiz.data.response.RestResponse;
import org.example.quiz.infrastructure.config.MessageSourceConfiguration;
import org.example.quiz.service.StudentService;
import org.example.quiz.util.RestResponseGenerator;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.example.quiz.constant.MessageConstant.STUDENT_ADDED;

@RestController
@RequestMapping(value = "v1/students", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class StudentController {

    private final StudentService studentService;

    @PostMapping()
    public ResponseEntity<RestResponse<String>> addStudent(@Valid @RequestBody StudentRequest request) {
        studentService.addStudent(request);
        return RestResponseGenerator.success(MessageSourceConfiguration.getMessage(STUDENT_ADDED));
    }
}
