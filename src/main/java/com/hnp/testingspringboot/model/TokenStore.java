package com.hnp.testingspringboot.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.util.Date;

@RedisHash("TokenStore")
public class TokenStore implements Serializable {

    @Indexed
    @Id
    private String token;

    @Indexed
    private String username;

    private Date expireDate;

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

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    @Override
    public String toString() {
        return "TokenStore{" +
                "token='" + token + '\'' +
                ", username='" + username + '\'' +
                ", expireDate=" + expireDate +
                '}';
    }
}
