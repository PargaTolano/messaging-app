package com.parga.messagingapp.DTO;

import lombok.Data;

@Data
public class SocketMessageDTO {
    private String from;
    private String to;
    private String content;
}
