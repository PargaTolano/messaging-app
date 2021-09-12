package com.parga.messagingapp.message;

import com.parga.messagingapp.DTO.CreateMessageDTO;
import com.parga.messagingapp.chat.Chat;
import com.parga.messagingapp.chat.ChatRepository;
import com.parga.messagingapp.user.User;
import com.parga.messagingapp.user.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {

    @Mock
    MessageRepository messageRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    ChatRepository chatRepository;

    @Autowired
    @InjectMocks
    private MessageService messageService;

    private User u1;
    private User u2;

    private Chat c;

    private Message m1;
    private Message m2;

    List<Message> messageList;

    @BeforeEach
    public void setUp(){


        u1 = new User(null, "test-user1", "t1p", null);
        u2 = new User(null, "test-user2", "t2p", null);

        when(userRepository.insert(any(User.class))).thenReturn(u1);

        User t1 = userRepository.insert(u1);

        System.out.println("$$$$$$$$$$$$$$$$$$$$$");
        System.out.println("Inserted User Value: " + t1);
        System.out.println("$$$$$$$$$$$$$$$$$$$$$");

        List<User> users = List.of(u1,u2);

        userRepository.insert(users);

        System.out.println("$$$$$$$$$$$$$$$$$$$$$");
        System.out.println(messageRepository);
        System.out.println("$$$$$$$$$$$$$$$$$$$$$");

        System.out.println("$$$$$$$$$$$$$$$$$$$$$");
        System.out.println(userRepository);
        System.out.println("$$$$$$$$$$$$$$$$$$$$$");

        System.out.println("$$$$$$$$$$$$$$$$$$$$$");
        System.out.println(chatRepository);
        System.out.println("$$$$$$$$$$$$$$$$$$$$$");

        m1 = new Message();
        m2 = new Message();

        m1.setText("Test text1");
        m2.setText("Test text2");

        messageList = List.of(m1, m2);
    }

    @AfterEach
    public void tearDown(){
        m1 = m2 = null;
        messageList = null;
    }

    @Test
    @DisplayName("Test Should Pass if a new Message is created with text and info")
    public void shouldCreateNewMessageWithText() {
        CreateMessageDTO createDTONoFile = new CreateMessageDTO();
        createDTONoFile.setUserId("61347dc5cd692a7fb1a5eb0b");
        createDTONoFile.setChatId("6135b7e345f4b613e2e69a71");
        createDTONoFile.setText("Test Just Text");

        Message message;

        try{
            message = messageService.createMessage(createDTONoFile, null);
        } catch (Exception e) {
            System.out.println("#########################################");
            System.out.println(e.getMessage());
            System.out.println("#########################################");
            message = null;
        }

        Assertions.assertTrue(message != null);
    }

    @Test
    @DisplayName("Test Should Pass if a new Message is created with a file and info")
    public void shouldCreateNewMessageWithFile() {
        CreateMessageDTO createDTONoFile = new CreateMessageDTO();
        createDTONoFile.setUserId("61347dc5cd692a7fb1a5eb0b");
        createDTONoFile.setChatId("6135b7e345f4b613e2e69a71");

        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "file.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello World!".getBytes());
        Message message;

        try{
            message = messageService.createMessage(createDTONoFile, mockFile);
        } catch (Exception e) {
            System.out.println("#########################################");
            System.out.println(e.getMessage());
            System.out.println("#########################################");
            message = null;
        }

        Assertions.assertTrue(message != null);
    }

    @Test
    @DisplayName("Test Should Pass if a new Message is created with text, info, and a file")
    public void shouldCreateNewMessageWithTextAndFile() {
        CreateMessageDTO createDTONoFile = new CreateMessageDTO();
        createDTONoFile.setUserId("61347dc5cd692a7fb1a5eb0b");
        createDTONoFile.setChatId("6135b7e345f4b613e2e69a71");
        createDTONoFile.setText("Test With Text and File");

        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "file.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello World!".getBytes());

        Message message;

        try{
            message = messageService.createMessage(createDTONoFile, mockFile);
        } catch (Exception e) {
            System.out.println("#########################################");
            System.out.println(e.getMessage());
            System.out.println("#########################################");
            message = null;        }

        Assertions.assertTrue(message != null);

        messageService.deleteReply(message.getId());
    }

//    @Test
//    @DisplayName("Test Should Pass if a new Message is not created with no text and no file")
//    public void shouldNotCreateNewMessageWithOutTextAndFile(){
//        CreateMessageDTO createDTONoFile = new CreateMessageDTO();
//        createDTONoFile.setUserId("61347dc5cd692a7fb1a5eb0b");
//        createDTONoFile.setChatId("6135b7e345f4b613e2e69a71");
//
//        Message message;
//
//        try{
//            message = messageService.createMessage(createDTONoFile, null);
//        } catch (Exception e) {
//            System.out.println("#########################################");
//            System.out.println(e.getMessage());
//            System.out.println("#########################################");
//            message = null;        }
//
//        Assertions.assertTrue(message == null);
//    }
//
//    @Test
//    @DisplayName("Test Should Pass if a new Message is not created with no chatId")
//    public void shouldNotCreateNewMessageWithOutChatId(){
//        CreateMessageDTO createDTONoFile = new CreateMessageDTO();
//        createDTONoFile.setUserId("61347dc5cd692a7fb1a5eb0b");
//        createDTONoFile.setText("Test");
//
//        Message message;
//
//        try{
//            message = messageService.createMessage(createDTONoFile, null);
//        } catch (Exception e) {
//            System.out.println("#########################################");
//            System.out.println(e.getMessage());
//            System.out.println("#########################################");
//            message = null;        }
//
//        Assertions.assertTrue(message == null);
//    }
//
//    @Test
//    @DisplayName("Test Should Pass if a new Message is not created with no userId")
//    public void shouldNotCreateNewMessageWithOutUserId(){
//        CreateMessageDTO createDTONoFile = new CreateMessageDTO();
//        createDTONoFile.setChatId("6135b7e345f4b613e2e69a71");
//        createDTONoFile.setText("Test");
//
//        Message message;
//
//        try{
//            message = messageService.createMessage(createDTONoFile, null);
//        } catch (Exception e) {
//            System.out.println("#########################################");
//            System.out.println(e.getMessage());
//            System.out.println("#########################################");
//            message = null;        }
//
//        Assertions.assertTrue(message == null);
//    }
}