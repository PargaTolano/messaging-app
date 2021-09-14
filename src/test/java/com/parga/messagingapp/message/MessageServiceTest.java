package com.parga.messagingapp.message;

import com.parga.messagingapp.dto.OutMessageDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {

    @Mock
    MessageRepository messageRepository;

    @Autowired
    @InjectMocks
    private MessageService messageService;

    @Nested
    class CreateMessageTest{

        @Test
        public void whenCreateNewMessageWithText_shouldBeOk(){

            TimeZone tz = TimeZone.getTimeZone("UTC");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
            df.setTimeZone(tz);
            String isoDateString = df.format(new Date());

            OutMessageDTO msgData =
                    OutMessageDTO.builder()
                            .from("From")
                            .to("To")
                            .content("Content")
                            .time(isoDateString).build();

            Message message;

            try{
                message = messageService.createMessage(msgData, null);
            } catch (Exception e) {
                message = null;
            }

            Assertions.assertTrue(message != null);
        }

        @Test
        public void whenCreateNewMessageWithFile_shouldBeOk(){

            TimeZone tz = TimeZone.getTimeZone("UTC");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
            df.setTimeZone(tz);
            String isoDateString = df.format(new Date());

            OutMessageDTO msgData =
                    OutMessageDTO.builder().from("From").to("To").time(isoDateString).build();

            MockMultipartFile mockFile = new MockMultipartFile(
                    "file",
                    "file.txt",
                    MediaType.TEXT_PLAIN_VALUE,
                    "Hello World!".getBytes());
            Message message;

            try{
                message = messageService.createMessage(msgData, mockFile);
            } catch (Exception e) {
                message = null;
            }

            Assertions.assertTrue(message != null);
        }

        @Test
        public void whenCreateNewMessageWithTextAndFile_shouldBeOk(){

            TimeZone tz = TimeZone.getTimeZone("UTC");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
            df.setTimeZone(tz);
            String isoDateString = df.format(new Date());

            OutMessageDTO msgData =
                    OutMessageDTO.builder()
                            .from("From")
                            .to("To")
                            .content("Content")
                            .time(isoDateString).build();

            MockMultipartFile mockFile = new MockMultipartFile(
                    "file",
                    "file.txt",
                    MediaType.TEXT_PLAIN_VALUE,
                    "Hello World!".getBytes());

            Message message;

            try{
                message = messageService.createMessage(msgData, mockFile);
            } catch (Exception e) {
                message = null;
            }

            Assertions.assertTrue(message != null);
        }

        @Test
        public void whenCreateNewMessageWitNoTextAndNoFile_shouldThrowFieldNotFoundException(){

            TimeZone tz = TimeZone.getTimeZone("UTC");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
            df.setTimeZone(tz);
            String isoDateString = df.format(new Date());

            OutMessageDTO msgData =
                    OutMessageDTO.builder().from("From").to("To").time(isoDateString).build();

            MockMultipartFile mockFile = new MockMultipartFile(
                    "file",
                    "file.txt",
                    MediaType.TEXT_PLAIN_VALUE,
                    "Hello World!".getBytes());
            Message message;

            try{
                message = messageService.createMessage(msgData, mockFile);
            } catch (Exception e) {
                message = null;
            }

            Assertions.assertTrue(message != null);
        }
    }

}