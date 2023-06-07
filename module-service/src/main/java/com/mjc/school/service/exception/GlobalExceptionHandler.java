package com.mjc.school.service.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler({AuthorLengthException.class})
    public ResponseEntity<Object> handleEntityLengthException(
            AuthorLengthException ex, WebRequest request, Locale locale) {
        Object[] args = {ex.getMinLength(), ex.getMaxLength(), ex.getName()};
        String message = messageSource.getMessage("authorLength", args, locale);
        return new ResponseEntity<>(fillBody(ex.getCode(), message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ContentLengthException.class})
    public ResponseEntity<Object> handleEntityLengthException(
            ContentLengthException ex, WebRequest request, Locale locale) {
        Object[] args = {ex.getMinLength(), ex.getMaxLength(), ex.getComment()};
        String message = messageSource.getMessage("contentLength", args, locale);
        return new ResponseEntity<>(fillBody(ex.getCode(), message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({TagLengthException.class})
    public ResponseEntity<Object> handleEntityLengthException(
            TagLengthException ex, WebRequest request, Locale locale) {
        Object[] args = {ex.getMinLength(), ex.getMaxLength(), ex.getComment()};
        String message = messageSource.getMessage("tagLength", args, locale);
        return new ResponseEntity<>(fillBody(ex.getCode(), message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({TitleLengthException.class})
    public ResponseEntity<Object> handleEntityLengthException(
            TitleLengthException ex, WebRequest request, Locale locale) {
        Object[] args = {ex.getMinLength(), ex.getMaxLength(), ex.getComment()};
        String message = messageSource.getMessage("titleLength", args, locale);
        return new ResponseEntity<>(fillBody(ex.getCode(), message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({CommentLengthException.class})
    public ResponseEntity<Object> handleEntityLengthException(
            CommentLengthException ex, WebRequest request, Locale locale) {
        Object[] args = {ex.getMinLength(), ex.getMaxLength(), ex.getComment()};
        String message = messageSource.getMessage("commentLength", args, locale);
        return new ResponseEntity<>(fillBody(ex.getCode(), message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AuthorNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(
            AuthorNotFoundException ex, WebRequest request, Locale locale) {
        Object[] args = {ex.getId()};
        String message = messageSource.getMessage("authorNotFound", args, locale);
        return new ResponseEntity<>(fillBody(ex.getCode(), message), HttpStatus.NOT_FOUND);
    }

    private static Map<String, Object> fillBody(String code, String message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", message);
        body.put("code", code);
        body.put("timestamp", LocalDateTime.now());
        return body;
    }

    @ExceptionHandler({CommentNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(
            CommentNotFoundException ex, WebRequest request, Locale locale) {
        Object[] args = {ex.getId()};
        String message = messageSource.getMessage("commentNotFound", args, locale);
        return new ResponseEntity<>(fillBody(ex.getCode(), message), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({NewsNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(
            NewsNotFoundException ex, WebRequest request, Locale locale) {
        Object[] args = {ex.getId()};
        String message = messageSource.getMessage("newsNotFound", args, locale);
        return new ResponseEntity<>(fillBody(ex.getCode(), message), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({TagNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(
            TagNotFoundException ex, WebRequest request, Locale locale) {
        Object[] args = {ex.getId()};
        String message = messageSource.getMessage("tagNotFound", args, locale);
        return new ResponseEntity<>(fillBody(ex.getCode(), message), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({DuplicateEntityException.class})
    public ResponseEntity<Object> handleEntityLengthException(
            DuplicateEntityException ex, WebRequest request, Locale locale) {
        String message = messageSource.getMessage("duplicateEntity", null, locale);
        return new ResponseEntity<>(fillBody(ex.getCode(), message), HttpStatus.BAD_REQUEST);
    }

}
