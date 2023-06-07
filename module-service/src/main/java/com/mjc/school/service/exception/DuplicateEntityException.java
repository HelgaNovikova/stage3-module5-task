package com.mjc.school.service.exception;

public class DuplicateEntityException extends RuntimeException {

    public String getCode() {
        return "40010";
    }

    public DuplicateEntityException() {
        super();
    }
}
