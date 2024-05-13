package com.punith.userservice;

import com.punith.userservice.model.User;
import com.punith.userservice.repository.UserRepository;
import com.punith.userservice.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SequencedCollection;

@SpringBootApplication
@EnableDiscoveryClient
public class UserserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserserviceApplication.class, args);
        System.out.println("hello");
    }

    @RestController
    @RequestMapping("/auth")
    public class AuthController {
        @Autowired
        private UserAuthService userAuthService;
        @Autowired private UserRepository userRepository;
        @Autowired KafkaTemplate<String, String> kafkaTemplate;

        @Autowired
        private AuthenticationManager authenticationManager;

        @PostMapping("/register")
        public String addNewUser(@RequestBody User user) {
            return userAuthService.saveUser(user);
        }


        @PostMapping("/login")
        public String getToken(@RequestBody User user) {
            try {
                UsernamePasswordAuthenticationToken useNameAuther = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
                System.out.println(authenticationManager.toString());
                Authentication authenticate = authenticationManager.authenticate(useNameAuther);
                if (authenticate.isAuthenticated()) {
                    return userAuthService.generateToken(user.getUsername(),user.getRole());
                } else {
                    throw new RuntimeException("invalid access");
                }
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        }

        @GetMapping("/validate")
        public String validateToken(@RequestHeader("token") String token) {
            userAuthService.validateToken(token);
            return "Token is valid";
        }
        @GetMapping("/{username}")
        public User getByUsername(@PathVariable String username) {
            return userRepository.findByUsername(username);
        }
        @GetMapping("/users")
        public SequencedCollection<User> getUsers() {
            return userRepository.findAll();
        }
        @PutMapping("/{username}")
        public User update(@RequestBody User user, @PathVariable String username) {
            Optional<User> optionalUser = Optional.ofNullable(userRepository.findByUsername(username));

            if (optionalUser.isPresent()) {
                // Update the existing user with new information
                User existingUser = optionalUser.get();
                existingUser.setUsername(user.getUsername());
                existingUser.setEmail(user.getEmail());

                return userRepository.save(existingUser);
            } else {
                throw new RuntimeException("User not found with ID: " + username);
            }
        }
        @DeleteMapping("/{username}")
        public String delete( @PathVariable String username) {
            Optional<User> optionalUser = Optional.ofNullable(userRepository.findByUsername(username));
            if (optionalUser.isPresent()) {
                userRepository.delete(optionalUser.get());
                return optionalUser.get().toString();
            } else {
                throw new RuntimeException("User not found with ID: " + username);
            }
        }
        @PostMapping("/userevents")
        public String addUserEvents(@RequestBody Map<String, ?> input) {
            kafkaTemplate.send("user-events", input.toString());
            return input.toString();
        }
    }




}
