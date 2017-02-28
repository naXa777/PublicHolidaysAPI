package com.avk.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;

import com.avk.common.ObjectNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler
{
	@ExceptionHandler({ObjectNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFound(Exception ex)
    {
        return ex.getMessage();
    }
	
	@ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String badArg(Exception ex)
    {
        return ex.getMessage();
    }
}
