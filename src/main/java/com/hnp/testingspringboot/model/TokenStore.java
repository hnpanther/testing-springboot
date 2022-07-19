package com.hnp.testingspringboot.model;

import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("TokenStore")
public class TokenStore implements Serializable {

    private String token;
    private String username;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
