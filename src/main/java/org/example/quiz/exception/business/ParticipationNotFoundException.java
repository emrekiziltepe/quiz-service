package org.example.quiz.exception.business;


import org.example.quiz.exception.base.BaseException;

public class ParticipationNotFoundException extends BaseException {

    public ParticipationNotFoundException(String message) {
        super(message);
    }
}
