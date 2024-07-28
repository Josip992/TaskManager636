package com.oss.unist.hr.controller;

import com.oss.unist.hr.service.LoginService;
import com.oss.unist.hr.service.UserService;
import com.oss.unist.hr.util.SecurityCheck;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import com.oss.unist.hr.dto.UserDTO;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;
//set JAVA_HOME=C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2023.2.5\jbr
//cd /d I:\DESKTOP\TaskManager636 - Copy
//mvnw spring-boot:run

//http://localhost:8080/h2-console -SHOW TABLES -SELECT * FROM table_name



import com.oss.unist.hr.model.*;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.UUID;

@Controller
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final LoginService loginService;


    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @GetMapping("/register")
    public String getRegisterPage(Model model) {

        model.addAttribute("registerRequest", new UserDTO());
        logger.info("Displaying register page.");

        return "registration/register_page";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model, HttpSession session) {
        var x = SecurityCheck.isUserLoggedInReturnToHome(session);
        if (x != null) return x;
        model.addAttribute("loginRequest", new UserDTO());
        return "login/login_page";
    }

   /* @PostMapping("/execute-login")
    public String Executelogin(@ModelAttribute UserDTO user, HttpSession session, Model model) {
        Logger logger = LoggerFactory.getLogger(getClass());

        logger.info("Received login request for user: {}", user.getEmail());

        try {
            userService.authenticate(user.getEmail(), user.getPassword());
        } catch (RuntimeException e) {
            logger.error("Authentication failed for user: {}. Error: {}", user.getEmail(), e.getMessage());
            model.addAttribute("error", e.getMessage());
            model.addAttribute("httpSession",session);
            model.addAttribute("loginRequest", user);
            return "login/login_page";
        }
        String username = userService.getUsernameByEmail(user.getEmail());
        return "redirect:/dashboard";
    }
*/

    @GetMapping("/dashboard")
    public String showWelcomePage(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");

        model.addAttribute("username", username);

        logger.info("Dashboard redirection for username: {}", session.getAttribute("username"));

        return "user/user_dashboard";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserDTO request, @RequestParam String confirmPassword, Model model) {
        try {
            if (!request.getPassword().equals(confirmPassword)) {
                throw new RuntimeException("Passwords do not match");
            }

            User registeredUser = userService.registerUser(request);

            model.addAttribute("userId", registeredUser.getId());
            return "redirect:/login";

        } catch (RuntimeException e){
            model.addAttribute("error", e.getMessage());
            model.addAttribute("registerRequest", request);
            return "registration/register_page";
        }
    }

    @GetMapping("/logout")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/login";
    }
}

