package com.mjc.school.service.exception;

public class AuthorLengthException extends RuntimeException {
    public int getMinLength() {
        return minLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public String getName() {
        return name;
    }

    private final int minLength;
    private final int maxLength;
    private final String name;

    public String getCode() {
        return "40001";
    }

    public AuthorLengthException(int minLength, int maxLength, String name) {
        super();
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.name = name;
    }

}