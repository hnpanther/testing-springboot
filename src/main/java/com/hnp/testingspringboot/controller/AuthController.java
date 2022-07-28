package com.hnp.testingspringboot.controller;


import com.hnp.testingspringboot.entity.User;
import com.hnp.testingspringboot.model.AuthRequest;
import com.hnp.testingspringboot.model.JWTResponse;
import com.hnp.testingspringboot.security.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {

        if(authRequest.getUsername() == null || authRequest.getPassword() == null) {
            return ResponseEntity.badRequest().build();
        }

        Authentication authenticate = null;
        try {
            authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(),
                    authRequest.getPassword()));
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }


        String token = this.jwtUtil.generateToken((User) authenticate.getPrincipal());



        return ResponseEntity.ok(new JWTResponse(token));

    }
}
