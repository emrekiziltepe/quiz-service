package org.example.quiz.exception.business;


import org.example.quiz.exception.base.BaseException;

public class TestNotFoundException extends BaseException {

    public TestNotFoundException(String message) {
        super(message);
    }
}
