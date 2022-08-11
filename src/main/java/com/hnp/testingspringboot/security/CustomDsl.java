package com.hnp.testingspringboot.security;

import com.hnp.testingspringboot.security.filter.CustomUsernamePasswordAuthentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

public class CustomDsl extends AbstractHttpConfigurer<CustomDsl, HttpSecurity> {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        CustomUsernamePasswordAuthentication customUsernamePasswordAuthentication =
                new CustomUsernamePasswordAuthentication(authenticationManager);
        customUsernamePasswordAuthentication.setFilterProcessesUrl("/authentication/login");
        http.addFilter(customUsernamePasswordAuthentication);
    }

    public static CustomDsl customDsl() {
        return new CustomDsl();
    }
}
