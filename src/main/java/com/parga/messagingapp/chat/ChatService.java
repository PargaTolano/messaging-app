package com.parga.messagingapp.chat;

import com.parga.messagingapp.user.User;
import com.parga.messagingapp.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserService userService;

    @Autowired
    public ChatService(ChatRepository chatRepository, UserService userService){
        this.chatRepository = chatRepository;
        this.userService = userService;
    }

    public List<Chat> getChats(){
        return chatRepository.findAll();
    }

    public Chat getChatById(String id){
        return chatRepository.findById(id).get();
    }

    public Chat createChat(List<String> participants){
        List<User> users = userService.getAllById(participants);
        Chat chat = new Chat();
        chat.setParticipants(users);
        return chatRepository.insert(chat);
    }

    public Chat createGroupChat(List<String> participants, String name){
        List<User> users = userService.getAllById(participants);
        Chat chat = new GroupChat(name);
        chat.setParticipants(users);
        return chatRepository.insert(chat);
    }
}
