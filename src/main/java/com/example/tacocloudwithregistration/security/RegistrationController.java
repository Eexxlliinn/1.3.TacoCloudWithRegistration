package com.example.tacocloudwithregistration.security;

import com.example.tacocloudwithregistration.repository.UserInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
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
        log.info("Form info: {}", form);
        userInfoRepo.save(form.toUserInfo(passwordEncoder));
        log.info("User info: {}", userInfoRepo.findByUsername("Eugene"));
        return "redirect:/login";
    }
}
