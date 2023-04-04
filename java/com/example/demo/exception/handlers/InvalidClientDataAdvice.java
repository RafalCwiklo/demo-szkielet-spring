package com.example.demo.exception.handlers;

import com.example.demo.exception.ExceptionResponse;
import com.example.demo.exception.InvalidClientDataException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class InvalidClientDataAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidClientDataException.class)
    public ExceptionResponse handleInvalidClientDataException(final InvalidClientDataException exception) {
        log.error(exception.getMessage());

        return ExceptionResponse.builder()
                .errorMessage(exception.getMessage())
                .errorCode(100)
                .errorTimestamp(LocalDateTime.now())
                .build();
    }

}
