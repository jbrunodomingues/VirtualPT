package com.brn.homebrew.controller.exceptions;

import com.brn.homebrew.controller.dto.BadCredentialsErrorDto;
import com.brn.homebrew.controller.dto.BadRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

/**
 * @author Bruno Domingues
 */
@ControllerAdvice
public class RestExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> badCredentialsExceptionHandler(RuntimeException e, WebRequest request) {
        BadCredentialsErrorDto badCredentialsErrorDto = new BadCredentialsErrorDto();
        badCredentialsErrorDto.setCode(UNAUTHORIZED.value());
        badCredentialsErrorDto.setMessage("bad credentials");
        return handleExceptionInternal(e, badCredentialsErrorDto, null, UNAUTHORIZED, request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> illegalArgumentExceptionHandler(RuntimeException e, WebRequest request) {
        BadRequestDto badRequestDto = new BadRequestDto();
        badRequestDto.setCode(BAD_REQUEST.value());
        badRequestDto.setMessage("bad request");
        return handleExceptionInternal(e, badRequestDto, null, BAD_REQUEST, request);
    }
}