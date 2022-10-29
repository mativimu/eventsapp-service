package com.mativimu.registrappservice.entities.user;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService, UserDetailsService {
    
    private final UserRepository userRepo;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.mativimu.registrappservice.entities.user.User user = userRepo.findByUsername(username);
        if(user == null){
            log.info("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        }
        log.info("User founded in the database: {}", user.getUsername());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), null);
    }

    @Override
    public com.mativimu.registrappservice.entities.user.User saveUser(com.mativimu.registrappservice.entities.user.User user) {
        log.info("Saving new user {} to the database", user.getUsername());
        log.info("password: {}", user.getPassword());
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public com.mativimu.registrappservice.entities.user.User getUser(String username) {
        log.info("Fetching {} user", username);
        User user = userRepo.findByUsername(username);
        log.info("user: {}", user);
        if(user == null){
            log.info("User not found in the database");
            return new User("none", "none", "none", "none", "none");
        }
        log.info("User founded in the database: {}", user.getUsername());
        return user;
      }

    @Override
    public List<com.mativimu.registrappservice.entities.user.User> getUsers() {
        log.info("Fetching all users");
        return userRepo.findAll();
    }
}
