package com.parga.messagingapp.chat;

import com.google.common.collect.Lists;
import com.parga.messagingapp.DTO.CreateGroupChatDTO;
import com.parga.messagingapp.exception.FieldNotFoundException;
import com.parga.messagingapp.user.User;
import com.parga.messagingapp.user.UserRepository;
import com.parga.messagingapp.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Chat> getChats(){
        return chatRepository.findAll();
    }

    public Chat getChatById(String id){
        return chatRepository.findById(id).get();
    }

    public Chat createChat(List<String> participants) throws Exception{

        if(participants == null || participants.isEmpty())
            throw new FieldNotFoundException("Chat Creation: Participant List must not be null or empty");

        List<User> users = Lists.newArrayList(userRepository.findAllById(participants));
        Chat chat = new Chat();
        chat.setParticipants(users);
        return chatRepository.insert(chat);
    }

    public Chat createGroupChat(CreateGroupChatDTO dto) throws Exception{

        if( dto == null )
            throw new FieldNotFoundException("Group Chat Creation: CreateGroupChat Request must not be empty");

        if( dto.getParticipants() == null || dto.getParticipants().isEmpty() )
            throw new FieldNotFoundException("Group Chat Creation: Participant List must not be null or empty");

        List<User> users = Lists.newArrayList(userRepository.findAllById(dto.getParticipants()));
        GroupChat chat = new GroupChat(dto.getName());
        chat.setParticipants(users);
        return chatRepository.insert(chat);
    }
}
