package org.example.quiz.exception.base;

import java.io.Serial;
import java.io.Serializable;

public abstract class BaseException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 5345122104392277009L;

    protected BaseException(String message) {
        super(message);
    }

    protected BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
