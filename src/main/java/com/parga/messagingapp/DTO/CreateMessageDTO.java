package com.parga.messagingapp.DTO;

import org.springframework.web.multipart.MultipartFile;

public class CreateMessageDTO {
    private String text;

    private String chatId;

    private String userId;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
