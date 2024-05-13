package com.punith.journal;


import com.punith.journal.model.JournalEntity;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserEventLisiners {
    @Autowired JournalRepository journalRepository;

    @KafkaListener(topics = "user-events",groupId = "groupId")
    public void listen1(String cont) {
        System.out.println(cont+"------------");
        JournalEntity jornalEnt = new JournalEntity();
        jornalEnt.setTitle("title");
        jornalEnt.setContent("content:"+cont);
        journalRepository.insert(jornalEnt);

    }
}
