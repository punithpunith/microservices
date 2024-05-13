package com.punith.journal;


import com.punith.journal.model.JournalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.SequencedCollection;

@RestController
@RequestMapping("/journal")
public class JournalController {

    @Autowired JournalRepository journalRepository;

    @GetMapping("/journals")
    public SequencedCollection<JournalEntity> getAllRecs(){
        return journalRepository.findAll();
    }
}
