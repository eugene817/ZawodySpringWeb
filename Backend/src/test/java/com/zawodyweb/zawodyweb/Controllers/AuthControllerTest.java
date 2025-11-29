package com.zawodyweb.zawodyweb.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zawodyweb.zawodyweb.database.dto.AuthenticationRequest;
import com.zawodyweb.zawodyweb.database.dto.RegisterRequest;
import com.zawodyweb.zawodyweb.database.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void register_ShouldReturnToken_WhenRequestIsValid() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setLogin("testuser");
        request.setEmail("test@example.com");
        request.setPassword("password123");
        request.setFirstName("Test");
        request.setLastName("User");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.login").value("testuser"));
    }

    @Test
    void login_ShouldReturnToken_WhenCredentialsAreCorrect() throws Exception {
        RegisterRequest register = new RegisterRequest();
        register.setLogin("loginuser");
        register.setEmail("login@example.com");
        register.setPassword("pass123");
        register.setFirstName("Name");

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(register)));

        AuthenticationRequest loginRequest = new AuthenticationRequest();
        loginRequest.setLogin("loginuser");
        loginRequest.setPassword("pass123");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    void login_ShouldReturn401_WhenPasswordIsWrong() throws Exception {
        RegisterRequest register = new RegisterRequest();
        register.setLogin("wrongpass");
        register.setEmail("wrong@example.com");
        register.setPassword("correctpass");

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(register)));

        AuthenticationRequest loginRequest = new AuthenticationRequest();
        loginRequest.setLogin("wrongpass");
        loginRequest.setPassword("WRONG_PASSWORD");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }
}