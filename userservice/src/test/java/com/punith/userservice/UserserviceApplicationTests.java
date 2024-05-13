package com.punith.userservice;

import com.punith.userservice.model.User;
import com.punith.userservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserserviceApplicationTests {
    @Autowired
    UserRepository repository;

    @Test
    void contextLoads() {
       System.out.println(repository.findAll());

        System.out.println(repository.count());
    }

}
