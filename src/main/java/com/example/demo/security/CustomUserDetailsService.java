package com.example.demo.security;

import com.example.demo.exception.UserServiceException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    Logger logger = LogManager.getLogger();

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findFirstByUsername(username);
        if(user != null){
            org.springframework.security.core.userdetails.User authUser =
                    new org.springframework.security.core.userdetails.User(
                            user.getLogin(),
                            user.getPassword(),
                            user.getRoles().stream().map((role) -> new SimpleGrantedAuthority(role.getName()))
                                    .collect(Collectors.toList())
            );
            logger.log(Level.INFO, "++++++++++++++++++++++> Valid username or password");
            return authUser;
        } else {
            logger.log(Level.INFO, "----------------------> Invalid username or password");
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }
}
