package com.zawodyweb.zawodyweb.Controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.zawodyweb.zawodyweb.database.dto.AuthenticationRequest
import com.zawodyweb.zawodyweb.database.dto.RegisterRequest
import com.zawodyweb.zawodyweb.database.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthControllerSpec extends Specification {

	@Autowired MockMvc mockMvc
	@Autowired UserRepository userRepository
	@Autowired ObjectMapper objectMapper

	def setup() {
		userRepository.deleteAll()
	}

	def "register should return token when request is valid"() {
		given: "valid registration request"
		def request = new RegisterRequest(
				login: "testuser",
				email: "test@example.com",
				password: "password123",
				firstName: "Test",
				lastName: "User"
				)

		expect: "call to register returns OK and token"
		mockMvc.perform(post("/api/auth/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath('$.token').isNotEmpty())
				.andExpect(jsonPath('$.login').value("testuser"))
	}

	def "login should return token when credentials are correct"() {
		given: "a registered user"
		def registerRequest = new RegisterRequest(
				login: "loginuser",
				email: "login@example.com",
				password: "pass123",
				firstName: "Name"
				)
		mockMvc.perform(post("/api/auth/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(registerRequest)))

		and: "login request with correct credentials"
		def loginRequest = new AuthenticationRequest(
				login: "loginuser",
				password: "pass123"
				)

		when: "logging in"
		def result = mockMvc.perform(post("/api/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginRequest)))

		then: "token is returned"
		result.andExpect(status().isOk())
				.andExpect(jsonPath('$.token').isNotEmpty())
	}

	def "login should return 401 when password is wrong"() {
		given: "a registered user"
		def registerRequest = new RegisterRequest(
				login: "wrongpass",
				email: "wrong@example.com",
				password: "correctpass"
				)
		mockMvc.perform(post("/api/auth/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(registerRequest)))

		and: "login request with wrong password"
		def loginRequest = new AuthenticationRequest(
				login: "wrongpass",
				password: "WRONG_PASSWORD"
				)

		when: "logging in"
		def result = mockMvc.perform(post("/api/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginRequest)))

		then: "status is Unauthorized"
		result.andExpect(status().isUnauthorized())
	}
}
