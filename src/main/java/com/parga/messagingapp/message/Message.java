package com.parga.messagingapp.message;

import com.parga.messagingapp.user.User;
import lombok.*;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZonedDateTime;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @Id
    @Setter(AccessLevel.NONE)
    private String id;

    private String from;
    private String to;
    private String content;
    private String time;

    @DBRef(lazy = true)
    private User sender;

    @DBRef(lazy = true)
    private User receiver;
}
