package com.example.tacocloudwithregistration.security;

import com.example.tacocloudwithregistration.data.UserInfo;
import com.example.tacocloudwithregistration.repository.UserInfoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.header.writers.frameoptions.WhiteListedAllowFromStrategy;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.Arrays;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;



@Configuration
@EnableWebSecurity
public class SecurityConfig {
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
                .requestMatchers(mvc.pattern("/"), mvc.pattern("/**"), mvc.pattern("/h2-console/**")).permitAll()
                .requestMatchers(toH2Console()).permitAll()
                .requestMatchers(mvc.pattern("/design"), mvc.pattern("/orders")).hasRole("USER")
                .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf
                        .csrfTokenRepository(csrfTokenRepository())
                        .ignoringRequestMatchers(toH2Console())  // disable CSRF for H2
                        .ignoringRequestMatchers(mvc.pattern("/h2-console/**"))  // disable CSRF for H2
                )
                .formLogin(form -> form
                    .loginPage("/login")
                    .permitAll()
                    .defaultSuccessUrl("/design")
                )
                .headers(headers ->
                        headers
                                .frameOptions(frameOptions -> frameOptions.disable())
                )
                .logout(logout ->
                        logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/")
                                .permitAll()
                );
        return http.build();
    }
    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setSessionAttributeName("_csrf");
        return repository;
    }
}
