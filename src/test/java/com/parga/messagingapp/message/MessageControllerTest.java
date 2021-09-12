package com.parga.messagingapp.message;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//TODO add a beforeEach and AfterEach Method to add potential dependencies
//TODO use gson to parse and use response data

@SpringBootTest
@AutoConfigureMockMvc
class MessageControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Nested
    class CreateMessageTest{
        @Test
        void whenCreateNewMessageWithTextChatIdAndUserId_shouldBeOk() throws Exception{
            MockMultipartHttpServletRequestBuilder multipartRequest =
                    MockMvcRequestBuilders.multipart("/api/v1/message/create");

            mockMvc.perform(
                            multipartRequest
                                    .param("text","gatito")
                                    .param("chatId", "6135b7e345f4b613e2e69a71")
                                    .param("userId", "61347dc5cd692a7fb1a5eb0b"))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @Test
        void whenCreateNewMessageWithFileChatIdAndUserId_shouldBeOk() throws Exception{
            MockMultipartFile file =
                    new MockMultipartFile("file", "Hello.txt", MediaType.TEXT_PLAIN_VALUE, "HelloWorld!".getBytes());

            MockMultipartHttpServletRequestBuilder multipartRequest =
                    MockMvcRequestBuilders.multipart("/api/v1/message/create");

            mockMvc.perform(
                            multipartRequest
                                    .file(file)
                                    .param("chatId", "6135b7e345f4b613e2e69a71")
                                    .param("userId", "61347dc5cd692a7fb1a5eb0b"))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @Test
        void whenCreateNewMessageWithTextFileChatIdAndUserId_shouldBeOk() throws Exception{
            MockMultipartFile file =
                    new MockMultipartFile("file", "Hello.txt", MediaType.TEXT_PLAIN_VALUE, "HelloWorld!".getBytes());

            MockMultipartHttpServletRequestBuilder multipartRequest =
                    MockMvcRequestBuilders.multipart("/api/v1/message/create");

            mockMvc.perform(
                            multipartRequest
                                    .file(file)
                                    .param("text","gatito")
                                    .param("chatId", "6135b7e345f4b613e2e69a71")
                                    .param("userId", "61347dc5cd692a7fb1a5eb0b"))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @Test
        void whenCreateNewMessageWithTextChatId_shouldBe5xx() throws Exception{
            MockMultipartHttpServletRequestBuilder multipartRequest =
                    MockMvcRequestBuilders.multipart("/api/v1/message/create");

            mockMvc.perform(
                            multipartRequest
                                    .param("text","gatito")
                                    .param("chatId", "6135b7e345f4b613e2e69a71"))
                    .andDo(print())
                    .andExpect(status().is5xxServerError());
        }

        @Test
        void whenCreateNewMessageWithTextUserId_shouldBe5xx() throws Exception{
            MockMultipartHttpServletRequestBuilder multipartRequest =
                    MockMvcRequestBuilders.multipart("/api/v1/message/create");

            mockMvc.perform(
                            multipartRequest
                                    .param("text","gatito")
                                    .param("userId", "61347dc5cd692a7fb1a5eb0b"))
                    .andDo(print())
                    .andExpect(status().is5xxServerError());
        }

        @Test
        void whenCreateNewMessageWithChatIdAndUserId_shouldBe5xx() throws Exception{
            MockMultipartHttpServletRequestBuilder multipartRequest =
                    MockMvcRequestBuilders.multipart("/api/v1/message/create");

            mockMvc.perform(
                            multipartRequest
                                    .param("text","gatito")
                                    .param("chatId", "6135b7e345f4b613e2e69a71")
                                    .param("userId", "61347dc5cd692a7fb1a5eb0b"))
                    .andDo(print())
                    .andExpect(status().is5xxServerError());
        }
    }

    @Nested
    class CreateReplyTest{
        @Test
        void whenCreateNewReplyMessageWithTextChatIdAndUserId_shouldBeOk() throws Exception{
            MockMultipartHttpServletRequestBuilder multipartRequest =
                    MockMvcRequestBuilders.multipart("/api/v1/message/create-reply");

            mockMvc.perform(
                            multipartRequest
                                    .param("text","gatito")
                                    .param("chatId", "6135b7e345f4b613e2e69a71")
                                    .param("userId", "61347dc5cd692a7fb1a5eb0b"))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @Test
        void whenCreateNewReplyMessageWithTextFileChatIdAndUserId_shouldBeOk() throws Exception{
            MockMultipartFile file = new MockMultipartFile("file", "Hello.txt", MediaType.TEXT_PLAIN_VALUE, "HelloWorld!".getBytes());

            MockMultipartHttpServletRequestBuilder multipartRequest =
                    MockMvcRequestBuilders.multipart("/api/v1/message/create-reply");

            mockMvc.perform(
                            multipartRequest
                                    .file(file)
                                    .param("text","gatito")
                                    .param("chatId", "6135b7e345f4b613e2e69a71")
                                    .param("userId", "61347dc5cd692a7fb1a5eb0b"))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @Test
        void whenCreateNewReplyMessageWithTextChatId_shouldBe5xx() throws Exception{
            MockMultipartHttpServletRequestBuilder multipartRequest =
                    MockMvcRequestBuilders.multipart("/api/v1/message/create-reply");

            mockMvc.perform(
                            multipartRequest
                                    .param("text","gatito")
                                    .param("chatId", "6135b7e345f4b613e2e69a71"))
                    .andDo(print())
                    .andExpect(status().is5xxServerError());
        }

        @Test
        void whenCreateNewReplyMessageWithTextUserId_shouldBe5xx() throws Exception{
            MockMultipartHttpServletRequestBuilder multipartRequest =
                MockMvcRequestBuilders.multipart("/api/v1/message/create-reply");


            mockMvc.perform(
                            multipartRequest
                                    .param("text","gatito")
                                    .param("userId", "61347dc5cd692a7fb1a5eb0b"))
                    .andDo(print())
                    .andExpect(status().is5xxServerError());
        }

        @Test
        void whenCreateNewReplyMessageWithChatIdAndUserId_shouldBe5xx() throws Exception{
            MockMultipartHttpServletRequestBuilder multipartRequest =
                    MockMvcRequestBuilders.multipart("/api/v1/message/create-reply");

            mockMvc.perform(
                            multipartRequest
                                    .param("chatId", "6135b7e345f4b613e2e69a71")
                                    .param("userId", "61347dc5cd692a7fb1a5eb0b"))
                    .andDo(print())
                    .andExpect(status().is5xxServerError());
        }
    }
}