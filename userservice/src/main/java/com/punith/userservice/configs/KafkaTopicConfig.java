package com.punith.userservice.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class KafkaTopicConfig {


    @Bean
    public NewTopic newtopic() {
        return new NewTopic("user-events", 1, (short) 1);
    }

}
