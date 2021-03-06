package com.hnp.testingspringboot.controller;


import com.hnp.testingspringboot.entity.User;
import com.hnp.testingspringboot.model.TokenStore;
import com.hnp.testingspringboot.redisrepo.TokenStoreRepository;
import com.hnp.testingspringboot.repository.UserRepository;
import com.hnp.testingspringboot.security.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TestController {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private TokenStoreRepository tokenStoreRepository;

    @GetMapping("/test")
    public User test() {

//        User user = this.userRepository.findUserByUsername("user").get();
//
//        for(GrantedAuthority s: user.getAuthorities()) {
//            System.out.println(s.getAuthority());
//        }

//        TokenStore tokenStore = new TokenStore();
//        tokenStore.setToken("sadfj;salfjwl;");
//        tokenStore.setUsername("user");

//        redisTemplate.opsForHash().put("TokenStore",tokenStore.getToken(), tokenStore);
        List<TokenStore> list = redisTemplate.opsForHash().values("TokenStore");
        for(TokenStore t: list) {
            System.out.println(t.getToken());
            System.out.println(t.getUsername());
        }

//        redisTemplate.opsForHash().getOperations().get

//        TokenStore tokenStore1 = this.jwtUtil.getTokenStroe(user);
//        System.out.println(tokenStore1.getToken());


        //token = sadfj;salfjwl;
        //user = user


//        TokenStore tokenStore = new TokenStore();
//        tokenStore.setToken("dfjglvkdfjgregjfad;jfas");
//        tokenStore.setUsername("user");
//        this.tokenStoreRepository.save(tokenStore);

        TokenStore findToken = tokenStoreRepository.findById("dfjglvkdfjgregjfad;jfas").get();
        System.out.println(findToken);



        return null;
    }

    @GetMapping("user")
    public String getUser() {
        return "User return";
    }
}

