package com.parga.messagingapp.message;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MessageControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService messageService;

    @Test
    @DisplayName("Test Should Pass when new message with image is created")
    void newMessageShouldBeCreatedWithImage() throws Exception{

        MockMultipartFile file = new MockMultipartFile("file", "Hello.txt", MediaType.TEXT_PLAIN_VALUE, "HelloWorld!".getBytes());

        MockMultipartHttpServletRequestBuilder multipartRequest = MockMvcRequestBuilders.multipart("/api/v1/message/create");

        mockMvc.perform(
                multipartRequest
                        //.file(file)
                        .param("text","gatito")
                        .param("chatId", "6135b7e345f4b613e2e69a71")
                        .param("userId", "61347dc5cd692a7fb1a5eb0b")
        ).andDo(print()).andExpect(status().isOk());
    }
}