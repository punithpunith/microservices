package com.punith.userservice.configs;

import com.punith.userservice.model.User;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

//@Aspect
@Component
public class PAspect {


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Before("execution(* com.punith.userservice.*.*(..))")
    public void beforeRegisterUser(String user) {
        kafkaTemplate.send("user-events",String.valueOf(user));
    }

    @AfterReturning(pointcut = "execution(* com.punith.userservice.*.*(..))")
    public void afterUpdateUser(String user) {
        kafkaTemplate.send("user-events",String.valueOf(user));
    }

    // Define other pointcuts and advices for other user events as needed
}