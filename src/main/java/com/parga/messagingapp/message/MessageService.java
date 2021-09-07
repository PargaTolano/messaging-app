package com.parga.messagingapp.message;

import com.parga.messagingapp.DTO.CreateMessageDTO;
import com.parga.messagingapp.DTO.CreateReplyDTO;
import com.parga.messagingapp.chat.Chat;
import com.parga.messagingapp.chat.ChatService;
import com.parga.messagingapp.user.User;
import com.parga.messagingapp.user.UserService;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserService userService;
    private final ChatService chatService;

    @Autowired
    public MessageService(
            MessageRepository messageRepository,
            UserService userService,
            ChatService chatService){
        this.messageRepository = messageRepository;
        this.userService = userService;
        this.chatService = chatService;
    }

    public List<Message> getMessages(){
        return messageRepository.findAllByOrderByTimeDesc();
    }

    public Message getMessageById(String id){ return  messageRepository.findById(id).get(); }

    public Message createMessage(CreateMessageDTO dto, MultipartFile image) throws Exception {

        if ( image == null && (dto.getText() == null || dto.getText().isEmpty()) )
            throw new Exception("Message must have text or image");

        if ( dto.getChatId() == null || dto.getChatId().isEmpty() )
            throw new Exception("chatID Obligatory");

        if ( dto.getUserId() == null || dto.getUserId().isEmpty() )
            throw new Exception("userId Obligatory");

        Message message = new Message(dto.getText());

        User user = userService.getUserById(dto.getUserId());
        Chat chat = chatService.getChatById(dto.getChatId());

        if ( user == null )
            throw new Exception("User not found");

        if ( chat == null )
            throw new Exception("Chat not found");

        message.setOwner(user);
        message.setChat(chat);

        if (image != null)
            message.setImage(new Binary(BsonBinarySubType.BINARY, image.getBytes()));

        message = messageRepository.insert(message);

        return message;
    }

    public ReplyMessage createReply(CreateReplyDTO dto, MultipartFile image) throws Exception{

        if ( image == null && (dto.getText() == null || dto.getText().isEmpty()) )
            throw new Exception("Message must have text or image");

        if ( dto.getChatId() == null || dto.getChatId().isEmpty() )
            throw new Exception("chatID Obligatory");

        if ( dto.getUserId() == null || dto.getUserId().isEmpty() )
            throw new Exception("userId Obligatory");

        ReplyMessage replyMessage = new ReplyMessage(dto.getText());

        User user = userService.getUserById(dto.getUserId());
        Chat chat = chatService.getChatById(dto.getChatId());
        Message parent = getMessageById(dto.getOwnerId());

        if ( user == null )
            throw new Exception("User not found");

        if ( chat == null )
            throw new Exception("Chat not found");

        if ( parent == null )
            throw new Exception("Owner Message not found");

        replyMessage.setOwner(user);
        replyMessage.setChat(chat);
        replyMessage.setParent(parent);

        if ( image != null )
            replyMessage.setImage(new Binary(BsonBinarySubType.BINARY, image.getBytes()));

        replyMessage = messageRepository.insert(replyMessage);

        return replyMessage;
    }
}
