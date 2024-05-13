package com.punith.journal;

import com.punith.journal.model.JournalEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalRepository extends MongoRepository<JournalEntity, String> {
}
