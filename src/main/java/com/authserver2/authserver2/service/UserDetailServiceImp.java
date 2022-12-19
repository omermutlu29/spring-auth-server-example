package com.authserver2.authserver2.service;

import com.authserver2.authserver2.model.User;
import com.authserver2.authserver2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailServiceImp implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();

    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.getByUsername(username);
        if (user.isPresent()){
            return user.get();
        }
        throw new UsernameNotFoundException("User could not found by following username "+ username);
    }

    public User createUser() {
        User user = new User("omermutlu", this.passwordEncoder().encode("Omer@2015"), true, true, true, true);
        return this.userRepository.save(user);
    }
}
