package ua.peresvit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.peresvit.entity.User;
import ua.peresvit.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;


@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messages;

    @RequestMapping(value = "login/success")
    public String loginSuccess() {
        if (userService.getCurrentUser().getRole().getRoleName().equals("ADMIN")) {
            return "redirect:/admin";
        }
        return "redirect:/home/workField";
    }

    @RequestMapping(value = "/login/failure")
    public String loginFailure(RedirectAttributes model, Locale locale) {
        model.addFlashAttribute("message", messages.getMessage("message.loginFailure", null, locale));
//        model.addAttribute(new User());
        return "redirect:/home";
    }
}
