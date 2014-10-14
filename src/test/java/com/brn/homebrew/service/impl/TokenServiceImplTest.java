package com.brn.homebrew.service.impl;

import com.brn.homebrew.service.TokenService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Bruno Domingues
 */
public class TokenServiceImplTest {

    @Test
    public void shouldReturnUsername() throws Exception {
        //given
        String username = "username";
        TokenService tokenService = new TokenServiceImpl();
        String generatedToken = tokenService.createTokenForUser(username);
        //when
        String actualUsername = tokenService.tryToGetUsernameForToken(generatedToken);
        //then
        String expectedUsername = "username";
        assertEquals(expectedUsername, actualUsername);
    }

    @Test
    public void shouldReturnNullForUnknownToken() throws Exception {
        //given
        TokenService tokenService = new TokenServiceImpl();
        //when
        String username = tokenService.tryToGetUsernameForToken("unknownToken");
        //then
        assertNull(username);
    }

    @Test
    public void shouldReturnNullForExpired() throws Exception {
//        //given
//        CacheBuilder.
//        String username = "username";
//        TokenService tokenService = new TokenServiceImpl();
//        String generatedToken = tokenService.createTokenForUser(username);
//        //when
//        String actualUsername = tokenService.tryToGetUsernameForToken(generatedToken);
//        //then
//        String expectedUsername = "username";
//        assertEquals(expectedUsername, actualUsername);

    }
}
