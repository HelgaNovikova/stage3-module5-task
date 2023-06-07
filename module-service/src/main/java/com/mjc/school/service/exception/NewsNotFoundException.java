package com.mjc.school.service.exception;

public class NewsNotFoundException extends RuntimeException {

    public String getCode() {
        return "40403";
    }

    private final Long id;

    public Long getId() {
        return id;
    }

    public NewsNotFoundException(Long id) {
        super();
        this.id = id;
    }
}
