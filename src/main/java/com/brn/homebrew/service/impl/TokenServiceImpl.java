package com.brn.homebrew.service.impl;

import com.brn.homebrew.service.TokenService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author Bruno Domingues
 */
public class TokenServiceImpl implements TokenService {

    private static LoadingCache<String, String> tokenUsernameCacheMap;
    static {
        tokenUsernameCacheMap = CacheBuilder.newBuilder()
                .expireAfterAccess(30, TimeUnit.MINUTES)
                .maximumSize(1000)
                .build(
                        new CacheLoader<String, String>() {
                            @Override
                            public String load(String key) throws Exception {
                                throw new Exception();
                            }
                        }
                );
    }
    private SecureRandom random = new SecureRandom();

    @Override
    public String createTokenForUser(String username) {
        String token = new BigInteger(130, random).toString(32);
        tokenUsernameCacheMap.put(token, username);
        return token;
    }

    @Override
    public String tryToGetUsernameForToken(String token) {
        try {
            return tokenUsernameCacheMap.get(token);
        } catch (Exception e) {
            //ignore this exception on purpose. It means that key wasn't found
            return null;
        }
    }
}
