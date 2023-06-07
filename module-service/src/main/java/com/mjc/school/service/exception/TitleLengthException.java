package com.mjc.school.service.exception;

public class TitleLengthException extends RuntimeException {

    public int getMinLength() {
        return minLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public String getComment() {
        return comment;
    }

    private final int minLength;
    private final int maxLength;
    private final String comment;

    public String getCode() {
        return "40005";
    }

    public TitleLengthException(int minLength, int maxLength, String comment) {
        super();
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.comment = comment;
    }
}
