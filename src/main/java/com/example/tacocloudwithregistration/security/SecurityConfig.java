package com.example.tacocloudwithregistration.security;

import com.example.tacocloudwithregistration.data.UserInfo;
import com.example.tacocloudwithregistration.repository.UserInfoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.WhiteListedAllowFromStrategy;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.Arrays;

@Configuration
public class SecurityConfig extends SecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserInfoRepository userInfoRepo) {
        return username -> {
            UserInfo userInfo = userInfoRepo.findByUsername(username);
            if (userInfo != null) {
                return userInfo;
            } else {
                throw new UsernameNotFoundException("User '" + username + "' not found");
            }
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvc = new MvcRequestMatcher.Builder(introspector);
        http
                .authorizeHttpRequests(auth -> auth
                .requestMatchers(mvc.pattern("/design"), mvc.pattern("/orders")).hasRole("USER")
                .requestMatchers(mvc.pattern("/"), mvc.pattern("/**"), mvc.pattern("/h2-console/**")).permitAll()
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(mvc.pattern("/h2-console/**"))  // disable CSRF for H2
                )
                .formLogin(form -> form
                    .loginPage("/login")
                    .permitAll()
                    .defaultSuccessUrl("/design")
                );
        return http.build();
    }
}
