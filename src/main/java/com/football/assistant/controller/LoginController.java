package com.football.assistant.controller;

import javax.validation.Valid;
import com.football.assistant.domain.User;
import com.football.assistant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    private boolean adminAccountReady = false;

    @GetMapping("/login")
    public ModelAndView login(){
        if(!adminAccountReady) {
            createAdminAccount();
            adminAccountReady = true;
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/registration")
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping("/registration")
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult.rejectValue("email", "error.user",
                    "This email is already in use");
        }
        if (user.getEmail().equals("admin")) {
            bindingResult.rejectValue("email", "error.user",
                    "This email is reserved");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user, "NORMAL");
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");
        }
        return modelAndView;
    }

    @GetMapping("/access-denied")
    public ModelAndView accessDenied(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("access-denied");
        return modelAndView;
    }

    /* Add the admin account if necessary */
    private void createAdminAccount() {
        User adminUser = new User();
        adminUser.setPassword("admin");
        adminUser.setEmail("admin@admin");
        adminUser.setNickname("admin");
        User userExists = userService.findUserByEmail(adminUser.getEmail());
        if (userExists != null) return;
        userService.saveUser(adminUser, "ADMIN");
    }

}
