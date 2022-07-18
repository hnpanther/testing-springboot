package com.hnp.testingspringboot.service;

import com.hnp.testingspringboot.entity.User;
import com.hnp.testingspringboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> optionalUser = this.userRepository.findUserByUsername(username);

        if(optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("username not found");
        }

        return optionalUser.get();
    }
}
