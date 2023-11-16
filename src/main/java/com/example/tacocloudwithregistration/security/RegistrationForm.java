package com.example.tacocloudwithregistration.security;

import com.example.tacocloudwithregistration.data.UserInfo;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegistrationForm {
    private String username;
    private String password;
    private String fullname;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phone;

    public UserInfo toUserInfo(PasswordEncoder passwordEncoder) {
        return new UserInfo(username, passwordEncoder.encode(password),
                fullname, street, city, state, zip, phone);
    }
}
