package com.parga.messagingapp.chat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenCreateChatWithParticipantList_ShouldBeOk() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/chat/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(" {\"participants\":[\"61347dc5cd692a7fb1a5eb0b\",\"61347e2ccd692a7fb1a5eb0d\"]}")
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void whenCreateChatWithoutParticipantList_ShouldThrowException() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders
                    .post("/api/v1/chat/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(" {\"participants\":[]}")
        ).andDo(print()).andExpect(status().is5xxServerError());
    }

    @Test
    public void whenCreateGroupChatWithParticipantList_ShouldBeOk() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/chat/create-group")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("name", "test-group-name")
                        .content(" {\"participants\":[\"61347dc5cd692a7fb1a5eb0b\",\"61347e2ccd692a7fb1a5eb0d\"]}")
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void whenCreateGroupChatWithoutParticipantList_ShouldThrowException() throws Exception{

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/chat/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("name", "test-group-name")
                        .content(" {\"participants\":[]}")
        ).andDo(print()).andExpect(status().is5xxServerError());
    }
}