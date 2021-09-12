package com.parga.messagingapp.chat;

import com.parga.messagingapp.message.Message;
import com.parga.messagingapp.user.User;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "chats")
@TypeAlias("chat")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chat {

    @Id
    @Setter(AccessLevel.NONE)
    private String id;

    @DBRef(lazy = true)
    private List<User> participants;

    @DBRef(lazy = true)
    private List<Message> messages;
}
