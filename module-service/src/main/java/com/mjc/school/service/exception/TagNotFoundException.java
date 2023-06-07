package com.mjc.school.service.exception;

public class TagNotFoundException extends RuntimeException {

    public String getCode() {
        return "40404";
    }

    private final Long id;

    public Long getId() {
        return id;
    }

    public TagNotFoundException(Long id) {
        super();
        this.id = id;
    }
}
