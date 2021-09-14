package com.parga.messagingapp.user;

import com.parga.messagingapp.message.Message;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document( collection = "users" )
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Setter(AccessLevel.NONE)
    private String    id;

    @Indexed(unique=true)
    @NotNull
    private String      username;

    @NotNull
    private String      password;

    @DBRef(lazy = true)
    private List<Message> messages;
}
