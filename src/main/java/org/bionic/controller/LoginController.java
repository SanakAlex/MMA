package org.bionic.controller;

import org.bionic.entity.User;
import org.bionic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

/**
 * Created by Alex Sanak on 11.11.2016.
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

//    @RequestMapping(value = "login")
//    public String login() {
//        return "login/login";
//    }

    @RequestMapping(value = "login/success")
    public String loginSuccess(Model model, Principal principal) {
        User loggedUser = userService.findUserByEmail(principal.getName());
        model.addAttribute("user", loggedUser);
        return "workField/office";
    }
}
