package com.parga.messagingapp.chat;

import com.parga.messagingapp.DTO.CreateChatDTO;
import com.parga.messagingapp.DTO.CreateGroupChatDTO;
import com.parga.messagingapp.DTO.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("api/v1/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping(path="create")
    public ResponseEntity createChat(@RequestBody CreateChatDTO createChat){
        try{
            Chat chat = chatService.createChat(createChat.getParticipants());
            return new ResponseEntity(new ResponseDTO("Created chat succesfully", chat), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(new ResponseDTO("Failed to create chat", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path="create-group")
    public ResponseEntity createGroupChat(@RequestBody CreateGroupChatDTO dto){
        try{
            Chat chat = chatService.createGroupChat(dto);
            return new ResponseEntity(new ResponseDTO("Created group-chat succesfully", chat), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(new ResponseDTO("Failed to create chat", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
