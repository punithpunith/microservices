package com.punith.userservice.service;

import com.punith.userservice.model.User;
import com.punith.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserAuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTService jwtService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> credential = Optional.ofNullable(userRepository.findByUsername(username));
        return credential.map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("user not found with name :" + username));
    }


    public String saveUser(User credential) {
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        if(userRepository.findByUsername(credential.getUsername())==null) {
            userRepository.save(credential);
            return "user added to the system";
        }else return "user already exists in the system";
    }


    public String generateToken(String username,String role) {
        return jwtService.generateToken(username, role);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }
}
