package com.parga.messagingapp.message;

import com.parga.messagingapp.chat.Chat;
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

    private String text;

    private ZonedDateTime time;

    private Binary image;

    @DBRef(lazy = true)
    private User owner;

    @DBRef(lazy = true)
    private Chat chat;

    public Message(String text) {
        this.text = text;
    }
}
