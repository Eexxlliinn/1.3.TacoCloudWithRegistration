package com.example.tacocloudwithregistration.security;

import com.example.tacocloudwithregistration.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    private UserInfoRepository userInfoRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(UserInfoRepository userInfoRepo, PasswordEncoder passwordEncoder) {
        this.userInfoRepo = userInfoRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String registerForm() {
        return "registration";
    }

    @PostMapping
    public String processRegistration(RegistrationForm form) {
        userInfoRepo.save(form.toUserInfo(passwordEncoder));
        return "redirect:/login";
    }
}
