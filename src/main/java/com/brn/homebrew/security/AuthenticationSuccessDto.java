package com.brn.homebrew.security;

/**
 * @author Bruno Domingues
 */
public class AuthenticationSuccessDto {

    private int code;
    private String sessionId;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
