package org.example.quiz.exception.handler;

import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.quiz.util.RestResponseGenerator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.MessageFormat;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException e,
                                                                   final HttpHeaders headers, final HttpStatusCode status,
                                                                   final WebRequest request) {
        log.error("handleNoHandlerFoundException {}; ", request, e);

        final var errorMessage =
                MessageFormat.format("No handler found for {0} {1}", e.getHttpMethod(), e.getRequestURL());
        return RestResponseGenerator.error(HttpStatus.NOT_FOUND, errorMessage);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e,
                                                                    final WebRequest request) {
        log.warn("handleIllegalArgumentException {}; ", request, e);
        return RestResponseGenerator.error(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException e,
                                                                         final HttpHeaders headers,
                                                                         final HttpStatusCode status,
                                                                         final WebRequest request) {
        log.warn("handleHttpRequestMethodNotSupported {}; ", request, e);

        final var errorMessageBuilder = new StringBuilder();
        errorMessageBuilder.append("method:").append(e.getMethod());
        errorMessageBuilder.append(" is not supported for this request. Supported methods are ");
        if (!CollectionUtils.isEmpty(e.getSupportedHttpMethods())) {
            e.getSupportedHttpMethods().forEach(t -> errorMessageBuilder.append(t).append(" "));
        }

        return RestResponseGenerator.error(HttpStatus.NOT_FOUND, errorMessageBuilder.toString());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException e,
                                                                     final HttpHeaders headers,
                                                                     final HttpStatusCode status,
                                                                     final WebRequest request) {
        log.warn("handleHttpMediaTypeNotSupported {}; ", request, e);

        final var errorMessageBuilder = new StringBuilder();
        errorMessageBuilder.append("media type:").append(e.getContentType());
        errorMessageBuilder.append(" is not supported. Supported media types are ");
        e.getSupportedMediaTypes().forEach(t -> errorMessageBuilder.append(t).append(" "));

        return RestResponseGenerator.error(HttpStatus.NOT_FOUND, errorMessageBuilder.toString());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {
        log.warn("handleHttpMessageNotReadable {}; ", request, e);

        return RestResponseGenerator.error((HttpStatus) status, e.getMessage());
    }

    @ExceptionHandler(TransactionSystemException.class)
    protected ResponseEntity<Object> handleTransactionException(TransactionSystemException e, final WebRequest request) {
        log.error("handleTransactionException {};", request, e);
        return handleAll(e, request);
    }

    @ExceptionHandler(JsonMappingException.class)
    protected ResponseEntity<Object> handleJsonMappingException(JsonMappingException e, final WebRequest request) {
        log.warn("handleJsonMappingException {}; ", request, e);
        return handleAll(e, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAll(final Exception e, final WebRequest request) {
        log.error("handleAll {}; ", request, e);

        final var errorMessage =
                MessageFormat.format("{0} Cause:{1}", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage());

        return RestResponseGenerator.error(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<Object> handleHttpMessageConversionException(HttpMessageConversionException e) {
        log.error("handleHttpMessageConversionException {0}", e);
        return RestResponseGenerator.error(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
    }
}
