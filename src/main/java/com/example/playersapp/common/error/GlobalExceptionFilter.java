package com.example.playersapp.common.error;

import com.example.playersapp.common.response.ApiResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@RestControllerAdvice
public class GlobalExceptionFilter {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Object> handleException(Exception e) {
        return new ApiResponse<Object>(Collections.singletonList(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Object>  handleBadRequestException(Exception e) {
        return new ApiResponse<Object>(Collections.singletonList(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExceptionNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Object>  handleNotFoundException(Exception e) {
        return new ApiResponse<Object>(Collections.singletonList(e.getMessage()), e.getMessage(), HttpStatus.NOT_FOUND);
    }
}