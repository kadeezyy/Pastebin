package com.example.pastebin;

import com.example.pastebin.controller.pasteControllers.ReadController;
import com.example.pastebin.dtos.SimplePaste;
import com.example.pastebin.dtos.UserDTO;
import com.example.pastebin.service.pasteServices.ReadService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.security.test.context.support.WithMockUser;

@AutoConfigureMockMvc
@SpringBootTest
public class RestTemplateTest {
    @Autowired
    MockMvc mockMvc;
    int i = 0;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser("kanat")
//    @RepeatedTest(100)
    public void testCreateMultiplePastes() throws Exception {
        var requestPaste = SimplePaste.builder()
                .text("Creating new paste text + " + i)
                .build();
        ++i;
        var mappedValue = objectMapper.writeValueAsString(requestPaste);
        mockMvc.perform(
                post("http://localhost:8080/v1/paste/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mappedValue)
        ).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "kanat", password = "password")
    public void testAuthorization() throws Exception {
        var requestUser = UserDTO.builder()
                .username("kanat")
                .password("password")
                .build();
        var mappedValue = objectMapper.writeValueAsString(requestUser);
        mockMvc.perform(post("http://localhost:8080/v1/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mappedValue))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "kanat")
    public void testGetPasteHandler() throws Exception {
        mockMvc.perform(
                get("http://localhost:8080/v1/paste/get/17")
        ).andExpect(status().isOk());
    }

}
