package com.parga.messagingapp.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@TypeAlias("group-chat")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupChat extends Chat{

    private String name;
}
