package com.parga.messagingapp.message;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZonedDateTime;

@Document()
@TypeAlias("reply")
public class ReplyMessage extends Message{

    @DBRef(lazy = true)
    private Message parent;

    public ReplyMessage(String text) {
        super(text);
    }

    public Message getParent() {
        return parent;
    }

    public void setParent(Message parent) {
        this.parent = parent;
    }
}
