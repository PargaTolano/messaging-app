package com.parga.messagingapp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientSocketMessageDTO {
    private String content;
    private String time;
    private String from;
}
