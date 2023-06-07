package com.mjc.school.service.exception;

public class AuthorNotFoundException extends RuntimeException {

    public String getCode() {
        return "40401";
    }

    private final Long id;

    public Long getId() {
        return id;
    }

    public AuthorNotFoundException(Long id) {
        super();
        this.id = id;
    }
}