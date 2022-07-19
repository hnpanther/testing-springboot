package com.hnp.testingspringboot.security.jwt;

import com.hnp.testingspringboot.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtil {

    private static final long EXPIRE_DATE = 60*60*60*1000;

    @Value("${securiry.jwt.secret}")
    private String secretKey;


    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims).setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DATE))
                .signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return getExpireDateFromToken(token).after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        return getClaims(token).getSubject();
    }

    public Date getExpireDateFromToken(String token) {
        return getClaims(token).getExpiration();
    }

    public Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }


}
