package org.example.quiz.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.quiz.data.request.TestAnswerRequest;
import org.example.quiz.data.response.RestResponse;
import org.example.quiz.data.response.TestQuestionResponse;
import org.example.quiz.data.response.TestResponse;
import org.example.quiz.data.response.TestResultResponse;
import org.example.quiz.service.TestService;
import org.example.quiz.util.RestResponseGenerator;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "v1/tests", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class TestController {

    private final TestService testService;

    @GetMapping()
    public ResponseEntity<RestResponse<Page<TestResponse>>> getAllTests(@ParameterObject Pageable pageable) {
        return RestResponseGenerator.success(testService.getAllTest(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestResponse<TestQuestionResponse>> getTest(@Valid @PathVariable("id") Long id) {
        return RestResponseGenerator.success(testService.getTest(id));
    }

    @PostMapping("/submit")
    public ResponseEntity<RestResponse<String>> submitTestAnswer(@Valid @RequestBody TestAnswerRequest request) {
        return RestResponseGenerator.success(testService.submitTestAnswer(request));
    }

    @GetMapping("/result")
    public ResponseEntity<RestResponse<TestResultResponse>> getTestResult(@Valid @RequestParam("test-id") Long testId,
                                                                          @Valid @RequestParam("student-id") Long studentId) {
        return RestResponseGenerator.success(testService.getResult(testId, studentId));
    }
}
