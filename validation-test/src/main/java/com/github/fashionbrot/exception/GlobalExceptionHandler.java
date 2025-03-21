package com.github.fashionbrot.exception;


import com.github.fashionbrot.common.util.ObjectUtil;
import com.github.fashionbrot.constraint.Violation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {



    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public Object exception(Exception e) {
        log.error("exception error:",e);
        return e.getMessage();
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.OK)
    public Object exception(BindException e) {
        String message =  String.join(",",  e.getBindingResult().getAllErrors().stream().map(m-> m.getDefaultMessage()).collect(Collectors.toList()));
        return message;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.OK)
    public Object exception(ConstraintViolationException e) {
        if (ObjectUtil.isNotEmpty(e.getConstraintViolations())) {
            Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
            String message =  String.join(",", constraintViolations.stream().map(m -> m.getMessage()).collect(Collectors.toList()));
            return message;
        }
        return e.getMessage();
    }

    @ExceptionHandler(ValidatedException.class)
    @ResponseStatus(HttpStatus.OK)
    public Object ValidatedException(ValidatedException e) {
        List<Violation> violations = e.getViolations();
        if (ObjectUtil.isEmpty(violations)){
            return e.getMessage();
        }else {
            return violations.stream().map(m -> m.getMessage()).collect(Collectors.joining(","));
        }
    }


}
