package com.parga.messagingapp.websocket;

import com.parga.messagingapp.dto.OutMessageDTO;
import com.parga.messagingapp.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static com.parga.messagingapp.Constants.*;

@Controller
public class SocketMessageController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping(SECURED_CHAT)
    @SendTo(SECURED_CHAT_HISTORY)
    public OutMessageDTO sendAll(@Payload MessageDTO msg) throws Exception {

        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(tz);
        String isoDateString = df.format(new Date());

        OutMessageDTO out =
                OutMessageDTO
                        .builder()
                        .from(msg.getFrom())
                        .to(msg.getTo())
                        .content(msg.getContent())
                        .time(isoDateString)
                        .build();

        return out;
    }

    @MessageMapping(SECURED_CHAT_ROOM)
    public void sendSpecific(@Payload MessageDTO msg, Principal user, @Header("simpSessionId") String sessionId) throws Exception{

        user.getName();

        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(tz);
        String isoDateString = df.format(new Date());

        OutMessageDTO out =
                OutMessageDTO
                        .builder()
                        .from(msg.getFrom())
                        .to(msg.getTo())
                        .content(msg.getContent())
                        .time(isoDateString)
                        .build();

        simpMessagingTemplate.convertAndSendToUser(
                msg.getTo(),
                "/secured/user/queue/specific-user",
                out
        );
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Msg greeting(Msg message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Msg("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

}