package org.example.quiz.util;

import lombok.experimental.UtilityClass;
import org.example.quiz.data.response.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@UtilityClass
public class RestResponseGenerator {

    public static <T> ResponseEntity<RestResponse<T>> success(String message) {

        var response = RestResponse.<T>builder()
                .message(message)
                .result(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static <T> ResponseEntity<RestResponse<T>> success(T responseData) {
        var response = RestResponse.<T>builder()
                .data(responseData)
                .result(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static ResponseEntity<Object> error(HttpStatus httpStatus, String errorMessage) {
        var response = RestResponse.builder()
                .message(errorMessage)
                .result(false)
                .build();
        return new ResponseEntity<>(response, httpStatus);
    }
}
