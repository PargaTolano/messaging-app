package com.parga.messagingapp.websocket;

import com.parga.messagingapp.DTO.ClientSocketMessageDTO;
import com.parga.messagingapp.DTO.SocketMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Controller
public class SocketMessageController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/secured/room")
    public void sendSpecific(
            @Payload SocketMessageDTO msg,
            Principal User,
            @Header("simpSessionId") String sessionId) throws Exception{

        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(tz);
        String isoDateString = df.format(new Date());

        ClientSocketMessageDTO out = new ClientSocketMessageDTO(
                msg.getContent(),
                isoDateString,
                msg.getFrom()
        );

        simpMessagingTemplate.convertAndSendToUser(msg.getTo(), "/secured/user/queue/specific-user", out);
    }
}