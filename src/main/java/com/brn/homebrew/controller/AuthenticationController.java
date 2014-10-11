package com.brn.homebrew.controller;

import com.brn.homebrew.controller.dto.AuthenticationDto;
import com.brn.homebrew.controller.dto.TokenDto;
import com.brn.homebrew.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author Bruno Domingues
 */
@RestController
@RequestMapping("authenticate")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public TokenDto authenticate(@RequestBody AuthenticationDto authenticationDto) {
        UsernamePasswordAuthenticationToken authenticationToken;
        String username = authenticationDto.getUsername();
        authenticationToken = new UsernamePasswordAuthenticationToken(
                username,
                authenticationDto.getPassword());
        confirmCredentialsOrThrowException(authenticationToken);
        String token = tokenService.createTokenForUser(username);
        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(token);
        return tokenDto;
    }

    private void confirmCredentialsOrThrowException(UsernamePasswordAuthenticationToken authenticationToken) {
        authenticationManager.authenticate(authenticationToken);
    }
}
