package savewise.savewise.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import savewise.savewise.dto.RegisterDto;
import savewise.savewise.service.UserService;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {

        model.addAttribute(
                "registerDto",
                new RegisterDto());

        return "register";
    }

    @PostMapping("/register")
    public String register(
            @ModelAttribute RegisterDto dto) {

        userService.register(dto);

        return "redirect:/login";
    }
    @PostMapping("/login")
    @ResponseBody
    public String testLogin() {
        return "POST LOGIN";
    }
}