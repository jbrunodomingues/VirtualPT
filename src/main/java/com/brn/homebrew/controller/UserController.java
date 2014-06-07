package com.brn.homebrew.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Bruno Domingues
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;


//    @RequestMapping(value = "authenticate", method = RequestMethod.GET)
//    @ResponseBody
//    @Transactional
//    public

}
