package com.zawodyweb.zawodyweb.Controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zawodyweb.zawodyweb.database.dto.ContestCreateRequest;
import com.zawodyweb.zawodyweb.database.dto.RegisterRequest;
import com.zawodyweb.zawodyweb.database.models.Contest;
import com.zawodyweb.zawodyweb.database.repositories.ContestRepository;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import com.zawodyweb.zawodyweb.database.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ContestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ContestRepository contestRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ObjectMapper objectMapper;

	private String jwtToken;

	@BeforeEach
	void setUp() throws Exception {
		contestRepository.deleteAll();
		userRepository.deleteAll();

		RegisterRequest registerRequest = new RegisterRequest();
		registerRequest.setLogin("testUser");
		registerRequest.setEmail("test@example.com");
		registerRequest.setPassword("password123");
		registerRequest.setFirstName("Test");
		registerRequest.setLastName("User");

		MvcResult result = mockMvc.perform(
				post("/api/auth/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(registerRequest)))
				.andExpect(status().isOk())
				.andReturn();

		String responseContent = result.getResponse().getContentAsString();
		JsonNode jsonNode = objectMapper.readTree(responseContent);

		String tokenRaw = jsonNode.get("token").asText();
		this.jwtToken = "Bearer " + tokenRaw;
	}

	@Test
	@WithMockUser(username = "testUser", roles = "USER")
	void contestCreateSucess() throws Exception {
		ContestCreateRequest request = new ContestCreateRequest();
		request.setName("testName");
		request.setDescription("TestDescription");
		request.setAccessPasssword("testpass");
		request.setVisible(true);

		mockMvc.perform(
				post("/api/contests")
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, jwtToken)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk());
		var savedContest = contestRepository.findAll().get(0);

		assertThat(savedContest.getName()).isEqualTo("testName");
	}

	@Test
	void getContest_ShouldReturnContest_WhenIdExists() throws Exception {
		Contest contest = Contest.builder()
				.name("Winter Cup")
				.description("Cold coding")
				.visible(true)
				.startTime(LocalDateTime.now())
				.build();
		contest = contestRepository.save(contest);
		mockMvc.perform(
				get("/api/contests")
						.header(HttpHeaders.AUTHORIZATION, jwtToken)
						.param("id", String.valueOf(contest.getId())))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(contest.getId()))
				.andExpect(jsonPath("$.name").value("Winter Cup"))
				.andExpect(jsonPath("$.description").value("Cold coding"));
	}

	@Test
	void getAllContests_ShouldReturnList() throws Exception {
		Contest c1 = Contest.builder().name("Contest A").visible(true).build();
		Contest c2 = Contest.builder().name("Contest B").visible(true).build();
		contestRepository.saveAll(List.of(c1, c2));

		mockMvc.perform(
				get("/api/contests/all")
						.header(HttpHeaders.AUTHORIZATION, jwtToken))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].name").exists())
				.andExpect(jsonPath("$[1].name").exists());
	}

	@Test
	void getAllContests_ShouldFail_WhenNoToken() throws Exception {
		mockMvc.perform(
				get("/api/contests/all"))
				.andExpect(status().isForbidden());
	}

	@Test
	void getContest_ShouldReturn404_WhenIdDoesNotExist() throws Exception {
		long nonExistentId = 999999L;

		mockMvc.perform(
				get("/api/contests/")
						.header(HttpHeaders.AUTHORIZATION, jwtToken)
						.param("id", String.valueOf(nonExistentId)))
				.andExpect(status().isNotFound());
	}

	@Test
	void getContest_ShouldReturn404_WhenParamIsMissing() throws Exception {
		mockMvc.perform(
				get("/api/contests/")
						.header(HttpHeaders.AUTHORIZATION, jwtToken))
				.andExpect(status().isNotFound());
	}

	@Test
	void getContest_ShouldReturn400_WhenIdIsNotNumber() throws Exception {
		mockMvc.perform(
				get("/api/contests")
						.header(HttpHeaders.AUTHORIZATION, jwtToken)
						.param("id", "not-a-number"))
				.andExpect(status().isBadRequest());
	}

}
