package com.javatpoint.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NegativeSalaryException  extends RuntimeException {
    public NegativeSalaryException(String message) {
        super(message);
    }
}
