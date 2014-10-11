package com.brn.homebrew.controller.dto;

/**
 * @author Bruno Domingues
 */
public class BadCredentialsErrorDto {

    public int code;
    public String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}