package com.punith.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data @NoArgsConstructor@AllArgsConstructor
@Document(collection = "user")
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private String role;
    private String email;
    private String phone;
    private String address;
    private String gender;
    private String birthday;

}
