package com.hnp.testingspringboot.security.jwt;

import com.hnp.testingspringboot.entity.User;
import com.hnp.testingspringboot.model.TokenStore;
import com.hnp.testingspringboot.redisrepo.TokenStoreRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class JWTUtil {

    // 1 minute = 60000 ms
    private static final long EXPIRE_DATE = 1*60000;

    @Value("${securiry.jwt.secret}")
    private String secretKey;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TokenStoreRepository tokenStoreRepository;


    public String generateToken(User user) {


        TokenStore tokenStore = getTokenStroe(user);

        if(tokenStore != null) {
            System.out.println("part1= " + tokenStore.getToken());
            return tokenStore.getToken();
        }
        System.out.println("part2=  token store is null");

        Map<String, Object> claims = new HashMap<>();
        Date expireDate = new Date(System.currentTimeMillis() + EXPIRE_DATE);
        String token = Jwts.builder().setClaims(claims).setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secretKey).compact();

        TokenStore newTokenStore = new TokenStore();
        newTokenStore.setToken(token);
        newTokenStore.setUsername(user.getUsername());
        newTokenStore.setExpireDate(expireDate);
        this.tokenStoreRepository.save(newTokenStore);
        System.out.println("part3= " + newTokenStore.getToken());
        return token;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return getExpireDateFromToken(token).after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateTokenForAccess(String token) {

        Optional<TokenStore> tokenStoreOptional = this.tokenStoreRepository.findById(token);
        if(tokenStoreOptional.isEmpty()) {
            return false;
        }
        return validateToken(token);
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

    public TokenStore getTokenStroe(User user) {
        Optional<TokenStore> tokenStoreOptional =
                this.tokenStoreRepository.findByUsername(user.getUsername());


        if(tokenStoreOptional.isEmpty()) {
            return null;
        }

        TokenStore tokenStore = tokenStoreOptional.get();

        String token = tokenStore.getToken();
        System.out.println(token);

        if(validateToken(token)) {
            return tokenStore;
        }
        this.tokenStoreRepository.deleteById(token);
        return null;

//        try {
//            if(getExpireDateFromToken(token).after(new Date())) {
//
//            } else {
//                return null;
//            }
//        } catch (ExpiredJwtException e) {
//            return null;
//        }




    }


}
