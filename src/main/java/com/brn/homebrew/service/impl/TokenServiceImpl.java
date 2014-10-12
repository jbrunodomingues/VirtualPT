package com.brn.homebrew.service.impl;

import com.brn.homebrew.service.TokenService;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Bruno Domingues
 */
public class TokenServiceImpl implements TokenService {

    //todo make thread safe and use guava to create a timed cache for token

    private static Map<String, String> tokenMap = new HashMap<>();
    private SecureRandom random = new SecureRandom();

    @Override
    public String createTokenForUser(String username) {
        String token = new BigInteger(130, random).toString(32);
        tokenMap.put(token, username);
        return token;
    }

    @Override
    public String getUser(String token) {
        return tokenMap.get(token);
    }
}
