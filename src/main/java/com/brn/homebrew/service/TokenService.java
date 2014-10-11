package com.brn.homebrew.service;

/**
 * @author Bruno Domingues
 */
public interface TokenService {

    String createTokenForUser(String username);

    String getUser(String token);
}