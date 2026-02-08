package com.zawodyweb.zawodyweb.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.zawodyweb.zawodyweb.database.dto.ProblemCreateRequest
import com.zawodyweb.zawodyweb.database.models.Contest
import com.zawodyweb.zawodyweb.database.repositories.ContestRepository
import com.zawodyweb.zawodyweb.database.repositories.ProblemRepository
import com.zawodyweb.zawodyweb.database.repositories.UserRepository
import com.zawodyweb.zawodyweb.database.dto.RegisterRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProblemControllerSpec extends Specification {

    @Autowired MockMvc mockMvc
    @Autowired ObjectMapper objectMapper
    @Autowired ContestRepository contestRepository
    @Autowired ProblemRepository problemRepository
    @Autowired UserRepository userRepository

    String token

    def setup() {
        problemRepository.deleteAll()
        contestRepository.deleteAll()
        userRepository.deleteAll()

        def regReq = [
                login: "admin",
                email: "admin@example.com",
                password: "password123",
                firstName: "Admin",
                lastName: "User"
        ]

        def mvcResult = mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(regReq)))
                .andExpect(status().isOk())
                .andReturn()

        def responseString = mvcResult.response.contentAsString
        def json = objectMapper.readTree(responseString)

        if (json.has("token")) {
            token = "Bearer " + json.get("token").asText()
        } else {
            throw new RuntimeException("Token not found in response! Response was: " + responseString)
        }
    }

    def "should create problem in contest"() {
        given: "an existing contest"
        def contest = contestRepository.save(Contest.builder()
                .name("Spring Contest")
                .description("Desc")
                .visible(true)
                .build())

        def request = ProblemCreateRequest.builder()
                .name("Sum Problem")
                .shortCode("A")
                .statement("Calculate A+B")
                .memoryLimitMb(256)
                .timeLimitMs(1000)
                .build()

        expect: "authorized request creates a problem"
        mockMvc.perform(post("/api/problems/contest/${contest.id}")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath('$.name').value("Sum Problem"))
                .andExpect(jsonPath('$.contestId').value(contest.id))
    }

    def "should return 400 for invalid problem request"() {
        given: "a contest"
        def contest = contestRepository.save(Contest.builder().name("Test").build())

        def request = new ProblemCreateRequest(
                name: "",
                shortCode: "A",
                statement: "Test"
        )

        expect:
        mockMvc.perform(post("/api/problems/contest/${contest.id}")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
    }
}