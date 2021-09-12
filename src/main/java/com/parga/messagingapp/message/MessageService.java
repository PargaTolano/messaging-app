package com.parga.messagingapp.message;

import com.parga.messagingapp.DTO.CreateMessageDTO;
import com.parga.messagingapp.DTO.CreateReplyDTO;
import com.parga.messagingapp.chat.Chat;
import com.parga.messagingapp.chat.ChatRepository;
import com.parga.messagingapp.chat.ChatService;
import com.parga.messagingapp.exception.FieldNotFoundException;
import com.parga.messagingapp.exception.IdNotFoundException;
import com.parga.messagingapp.exception.RequiredModelNullException;
import com.parga.messagingapp.user.User;
import com.parga.messagingapp.user.UserRepository;
import com.parga.messagingapp.user.UserService;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatRepository chatRepository;

    public List<Message> getMessages(){
        return messageRepository.findAllByOrderByTimeDesc();
    }

    public Message getMessageById(String id){ return  messageRepository.findById(id).get(); }

    public Message createMessage(CreateMessageDTO dto, MultipartFile image) throws Exception {

        if ( image == null && (dto.getText() == null || dto.getText().isEmpty()) )
            throw new FieldNotFoundException("Message must have text or image");

        if ( dto.getChatId() == null || dto.getChatId().isEmpty() )
            throw new IdNotFoundException("chatID Obligatory");

        if ( dto.getUserId() == null || dto.getUserId().isEmpty() )
            throw new IdNotFoundException("userId Obligatory");

        Message message = new Message(dto.getText());

        User user = userRepository.findById(dto.getUserId()).orElse(null);
        Chat chat = chatRepository.findById(dto.getChatId()).orElse(null);

        if ( user == null )
            throw new RequiredModelNullException("User not found");

        if ( chat == null )
            throw new RequiredModelNullException("Chat not found");

        message.setOwner(user);
        message.setChat(chat);

        if (image != null)
            message.setImage(new Binary(BsonBinarySubType.BINARY, image.getBytes()));

        message = messageRepository.insert(message);

        return message;
    }

    public ReplyMessage createReply(CreateReplyDTO dto, MultipartFile image) throws Exception{

        if ( image == null && (dto.getText() == null || dto.getText().isEmpty()) )
            throw new FieldNotFoundException("Message must have text or image");

        if ( dto.getChatId() == null || dto.getChatId().isEmpty() )
            throw new IdNotFoundException("chatID Obligatory");

        if ( dto.getUserId() == null || dto.getUserId().isEmpty() )
            throw new IdNotFoundException("userId Obligatory");

        ReplyMessage replyMessage = new ReplyMessage(dto.getText());

        User user = userRepository.findById(dto.getUserId()).orElse(null);
        Chat chat = chatRepository.findById(dto.getChatId()).orElse(null);
        Message parent = getMessageById(dto.getOwnerId());

        if ( user == null )
            throw new RequiredModelNullException("User not found");

        if ( chat == null )
            throw new RequiredModelNullException("Chat not found");

        if ( parent == null )
            throw new RequiredModelNullException("Owner Message not found");

        replyMessage.setOwner(user);
        replyMessage.setChat(chat);
        replyMessage.setParent(parent);

        if ( image != null )
            replyMessage.setImage(new Binary(BsonBinarySubType.BINARY, image.getBytes()));

        replyMessage = messageRepository.insert(replyMessage);

        return replyMessage;
    }

    public void deleteReply(String id) { messageRepository.deleteById(id); }
}
