package com.parga.messagingapp.message;

import com.parga.messagingapp.dto.OutMessageDTO;
import com.parga.messagingapp.exception.FieldNotFoundException;
import com.parga.messagingapp.user.UserRepository;
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

    public List<Message> getMessages(){
        return messageRepository.findAllByOrderByTimeDesc();
    }

    public Message getMessageById(String id){ return  messageRepository.findById(id).orElse(null); }

    public Message createMessage(OutMessageDTO dto, MultipartFile image) throws Exception {

        if ( image == null && (dto.getContent() == null || dto.getContent().isEmpty()) )
            throw new FieldNotFoundException("Message must have text or image");

        if ( dto.getFrom() == null || dto.getFrom().isEmpty() )
            throw new FieldNotFoundException("Message must have FROM user id");

        if ( dto.getTo() == null || dto.getTo().isEmpty() )
            throw new FieldNotFoundException("Message must have TO user id");

        Message msg = new Message();

        msg.setFrom(dto.getFrom());
        msg.setTo(dto.getTo());
        msg.setContent(dto.getContent());

        msg.setSender(null);
        msg.setReceiver(null);

        return messageRepository.insert(msg);
    }
}
