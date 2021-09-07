package com.parga.messagingapp.chat;

import com.parga.messagingapp.message.Message;
import com.parga.messagingapp.user.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "chats")
public class Chat {

    @Id
    private String id;

    @DBRef(lazy = true)
    private List<User> participants;

    @DBRef(lazy = true)
    private List<Message> messages;

    public String getId() {
        return id;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
