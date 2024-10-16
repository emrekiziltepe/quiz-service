package org.example.quiz.exception.business;


import org.example.quiz.exception.base.BaseException;

public class TestAlreadySubmittedException extends BaseException {

    public TestAlreadySubmittedException(String message) {
        super(message);
    }
}
