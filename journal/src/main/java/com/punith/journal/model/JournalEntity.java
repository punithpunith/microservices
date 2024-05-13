package com.punith.journal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Data @AllArgsConstructor@NoArgsConstructor
@Document(collection = "journal")
public class JournalEntity {
    @Id private String id;
    private String title;
    private String author;
    private String content;


}
