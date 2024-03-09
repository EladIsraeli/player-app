package com.example.playersapp.common.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.util.Collections;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private String message;
    private List<String> errors;
    private HttpStatus statusCode;
    private Integer count;

    // Constructor for success responses
    public ApiResponse(T data, String message, HttpStatus statusCode) {
        this.success = true;
        this.data = data;
        this.message = message;
        this.statusCode = statusCode;
        this.errors = null;
        this.count = calculateCount(data);
    }

    // Constructor for error responses
    public ApiResponse(List<String> errors, HttpStatus statusCode) {
        this.success = false;
        this.errors = errors;
        this.statusCode = statusCode;
        this.data = null;
        this.count = null;
    }

    public ApiResponse() {
        this.success = false;
        this.errors = Collections.emptyList();
        this.data = null;
        this.message = "";
        this.statusCode = HttpStatus.OK;
        this.count = null;
    }

    private Integer calculateCount(T data) {
        return data instanceof List<?> ? ((List<?>) data).size() : null;
    }

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public Integer getCount() {
        return count;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setData(T data) {
        this.data = data;
        this.count = calculateCount(data);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }
}
