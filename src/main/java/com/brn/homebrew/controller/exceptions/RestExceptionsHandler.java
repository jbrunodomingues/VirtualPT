package com.brn.homebrew.controller.exceptions;

import com.brn.homebrew.controller.dto.BadCredentialsErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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
}