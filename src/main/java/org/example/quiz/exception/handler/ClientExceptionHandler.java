package org.example.quiz.exception.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.quiz.exception.business.ParticipationNotFoundException;
import org.example.quiz.exception.business.TestNotFoundException;
import org.example.quiz.util.RestResponseGenerator;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ClientExceptionHandler {

    @ExceptionHandler(TestNotFoundException.class)
    public ResponseEntity<Object> handleTestNotFoundException(final TestNotFoundException e,
                                                              final WebRequest request) {
        log.error("handleTestNotFoundException {}", request, e);
        return RestResponseGenerator.error(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(ParticipationNotFoundException.class)
    public ResponseEntity<Object> handleParticipationNotFoundException(final ParticipationNotFoundException e,
                                                                       final WebRequest request) {
        log.error("handleParticipationNotFoundException {}", request, e);
        return RestResponseGenerator.error(HttpStatus.BAD_REQUEST, e.getMessage());
    }
}
