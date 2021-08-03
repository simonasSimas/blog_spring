package lt.codeacademy.blog_spring.controllers;

import lombok.extern.slf4j.Slf4j;
import lt.codeacademy.blog_spring.dtos.UserRegistrationDTO;
import lt.codeacademy.blog_spring.entities.User;
import lt.codeacademy.blog_spring.services.BlogService;
import lt.codeacademy.blog_spring.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@Slf4j
public class HomeController {

    public final UserService userService;

    public final BlogService blogService;

    public HomeController(UserService userService, BlogService blogService) {
        this.userService = userService;
        this.blogService = blogService;
    }

    @GetMapping
    public String home(HttpServletRequest request, HttpSession session) {
        return "redirect:/posts";
    }

    @GetMapping("/login")
    public String login(Model model, @AuthenticationPrincipal User user) {
        if (user == null) {
            model.addAttribute("user", new User());
            return "login";
        }
        return "redirect:/";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userRegistrationDTO", new UserRegistrationDTO());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid UserRegistrationDTO userRegistrationDTO, BindingResult bindingResult, Model model, HttpServletRequest request) throws ServletException {
        log.debug("Register request has been received. UserRegistrationDTO = {}, errors = {}, model = {}", userRegistrationDTO, bindingResult, model);

        if (bindingResult.hasErrors()) {
            log.warn("Invalid user {}", userRegistrationDTO);
            model.addAttribute("userRegistrationDTO", userRegistrationDTO);
            return "register";
        }

        if (!userRegistrationDTO.getPassword().equals(userRegistrationDTO.getPasswordConfirm())) {
            bindingResult.addError(new ObjectError("userRegistrationDTO", "Passwords do not match"));
            model.addAttribute("userRegistrationDTO", userRegistrationDTO);
            return "register";
        }

        if (userService.createUser(userRegistrationDTO) == null) {
            bindingResult.addError(new ObjectError("user", String.format("User with username '%s' already exists", userRegistrationDTO.getUsername())));
            model.addAttribute("userRegistrationDTO", userRegistrationDTO);
            return "register";
        }

        log.info("User {} has registered successfully", userRegistrationDTO);
        request.login(userRegistrationDTO.getUsername(), userRegistrationDTO.getPassword());
        userService.createUser(userRegistrationDTO);
        return "redirect:/";
    }
}
