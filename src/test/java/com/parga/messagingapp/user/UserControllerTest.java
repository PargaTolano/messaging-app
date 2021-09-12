package com.parga.messagingapp.user;

import com.parga.messagingapp.message.MessageService;
import org.apache.tomcat.jni.Time;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    class CreateUserTest{
        @Test
        public void whenCreateUserWithUsernameAndPassword_shouldBeOk() throws Exception{
            String username = "TestUserName" + System.currentTimeMillis();
            String password = "testpw";

            MvcResult result = mockMvc.perform(
                    MockMvcRequestBuilders
                            .post("/api/v1/user/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{ \"username\":\"" + username + "\",\"password\":\""+ password +"\" }")
            ).andDo(print()).andExpect(status().isOk()).andReturn();

            String response = result.getResponse().getContentAsString();

            Assertions.assertTrue(response.contains(username) && response.contains(password));
        }

        @Test
        public void whenCreateUserWithNoUsernameAndPassword_shouldBe5xx() throws Exception{
            String password = "testpw";
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .post("/api/v1/user/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{ \"password\":\""+ password +"\" }")
            ).andDo(print()).andExpect(status().is5xxServerError());
        }

        @Test
        public void whenCreateUserWithUsernameAndNoPassword_shouldBe5xx() throws Exception{
            String username = "TestUserName" + System.currentTimeMillis();
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .post("/api/v1/user/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{ \"username\":\"" + username + "\" }")
            ).andDo(print()).andExpect(status().is5xxServerError());
        }
    }

    @Nested
    class SearchUsersTest{
        @Test
        public void whenSearchUsersPagedWithQuery_shouldBeOk() throws Exception{
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/api/v1/user/search")
                            .param("query", "j")
                            .param("page" , "0")
                            .param("size" , "3")
            ).andDo(print()).andExpect(status().isOk());
        }

        @Test
        public void whenSearchUsersPagedWithNoQuery_shouldBeOk() throws Exception{
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/api/v1/user/search")
                            .param("query", "j")
                            .param("page" , "0")
                            .param("size" , "3")
            ).andDo(print()).andExpect(status().isOk());
        }

        @Test
        public void whenSearchUsersPagedWithNoPage_shouldBeOk() throws Exception{
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/api/v1/user/search")
                            .param("size" , "3")
            ).andDo(print()).andExpect(status().isOk());
        }

        @Test
        public void whenSearchUsersPagedWithNoSize_shouldBeOk() throws Exception{
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/api/v1/user/search")
                            .param("page" , "0")
            ).andDo(print()).andExpect(status().isOk());
        }

        @Test
        public void whenSearchUsersPagedWithNegativePage_shouldBe5xx() throws Exception {
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/api/v1/user/search")
                            .param("page" , "-1")
            ).andDo(print()).andExpect(status().is5xxServerError());
        }

        @Test
        public void whenSearchUsersPagedWithNegativeSize_shouldBe5xx() throws Exception {
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/api/v1/user/search")
                            .param("size" , "-1")
            ).andDo(print()).andExpect(status().is5xxServerError());
        }
    }

    @Nested
    class GetAllUsersTest{
        @Test
        public void whenGetAllUsersPaged_shouldBeOk() throws Exception{
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/api/v1/user")
                            .param("page", "0")
                            .param("size", "3")
            ).andDo(print()).andExpect(status().isOk());
        }

        @Test
        public void whenGetAllUsersPagedWithNegativePage_shouldBe5xx() throws Exception {
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/api/v1/user")
                            .param("page" , "-1")
            ).andDo(print()).andExpect(status().is5xxServerError());
        }

        @Test
        public void whenGetAllUsersPagedWithNegativeSize_shouldBe5xx() throws Exception {
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/api/v1/user")
                            .param("size" , "-1")
            ).andDo(print()).andExpect(status().is5xxServerError());
        }
    }

    @Nested
    class GetUserByIdTest{
        @Test
        public void whenGetUserByIdWithId_shouldBeOk() throws Exception{
            String id = "613af15e216fee1d45f901a6";
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/api/v1/user/"+id)
            ).andDo(print()).andExpect(status().isOk());
        }

        @Test
        public void whenGetUserByIdWithNonValidId_shouldBe5xx() throws Exception{
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/api/v1/user/non-valid-id")
            ).andDo(print()).andExpect(status().is5xxServerError());
        }
    }

    @Nested
    class UpdateUserTest{
        @Test
        public void whenUpdateUserWithIdAndUsername_shouldBeOk() throws Exception{
            String id = "613af15e216fee1d45f901a6";
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .put("/api/v1/user/update/"+ id)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content("{\"username\":\"Test Modified Value\"}")
            ).andDo(print()).andExpect(status().isOk());
        }

        @Test
        public void whenUpdateUserWithNonValidId_shouldBe5xx() throws Exception{
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .put("/api/v1/user/update/Non-Valid-Id")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content("{\"username\":\"Test Modified Value\"}")
            ).andDo(print()).andExpect(status().is5xxServerError());
        }

        @Test
        public void whenUpdateUserWithNoBody_shouldBe4xx() throws Exception{
            String id = "613af15e216fee1d45f901a6";
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .put("/api/v1/user/update/"+id)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
            ).andDo(print()).andExpect(status().is4xxClientError());
        }
    }

    @Nested
    class DeleteUserTest{
        @Test
        public void whenDeleteUserWithId_shouldBeOk() throws Exception{
            String id = "613af11e3e7a5b422029c9a2";
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .delete("/api/v1/user/delete/"+ id)
            ).andDo(print()).andExpect(status().isOk());
        }

        @Test
        public void whenDeleteUserWithNoId_shouldBe4xx() throws Exception{
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .delete("/api/v1/user/delete/")
            ).andDo(print()).andExpect(status().is4xxClientError());
        }

        @Test
        public void whenDeleteUserWithNonValidId_shouldBe5xx() throws Exception{
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .delete("/api/v1/user/delete/Non-Valid-Id")
            ).andDo(print()).andExpect(status().is5xxServerError());
        }
    }
}