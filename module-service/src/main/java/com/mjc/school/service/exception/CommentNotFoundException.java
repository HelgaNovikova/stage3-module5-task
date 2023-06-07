package com.mjc.school.service.exception;

public class CommentNotFoundException  extends RuntimeException {

    public String getCode() {
        return "40402";
    }

    private final Long id;

    public Long getId() {
        return id;
    }

    public CommentNotFoundException(Long id) {
        super();
        this.id = id;
    }
}
