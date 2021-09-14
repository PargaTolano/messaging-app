package com.parga.messagingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutMessageDTO extends MessageDTO {
    private String time;

    @Builder
    public OutMessageDTO(String from, String to, String content, String time){
        super(from, to, content);
        this.time = time;
    }
}
