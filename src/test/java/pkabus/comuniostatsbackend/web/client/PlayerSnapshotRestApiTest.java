package pkabus.comuniostatsbackend.web.client;

import static org.assertj.core.api.Assertions.assertThat;
import static pkabus.comuniostatsbackend.web.controller.FlatPlayerSnapshotController.BASE_FLAT_SNAPSHOTS;
import static pkabus.comuniostatsbackend.web.controller.FlatPlayerSnapshotController.CREATE;
import static pkabus.comuniostatsbackend.web.controller.PlayerController.ALL;
import static pkabus.comuniostatsbackend.web.controller.PlayerController.BASE_PLAYERS;
import static pkabus.comuniostatsbackend.web.controller.PlayerSnapshotController.BASE_PLAYERS_SNAPSHOTS;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pkabus.comuniostatsbackend.web.dto.FlatPlayerSnapshotDto;
import pkabus.comuniostatsbackend.web.dto.PlayerDto;
import pkabus.comuniostatsbackend.web.dto.PlayerSnapshotDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PlayerSnapshotRestApiTest {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void givenPlayerSnapshots_whenByPlayerId_then200Ok() {
		ResponseEntity<List<PlayerDto>> responseList = restTemplate.exchange(BASE_PLAYERS + ALL, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<PlayerDto>>() {
				});
		PlayerDto playerDto = responseList.getBody().get(0);

		ResponseEntity<List<PlayerSnapshotDto>> response = restTemplate.exchange(
				BASE_PLAYERS_SNAPSHOTS + "/" + playerDto.getId(), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<PlayerSnapshotDto>>() {
				});

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody()).hasSize(4) //
				.allMatch(dto -> dto.getMarketValue() == 160000);
	}

	@Test
	void useResourceJson_whenCreateAll_thenSuccess() throws JsonParseException, JsonMappingException, IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		File testJsonFile = new File(classLoader.getResource("1610117536_bundesliga_player.json").getFile());
		List<FlatPlayerSnapshotDto> flatPlayers = Arrays
				.asList(objectMapper.readValue(testJsonFile, FlatPlayerSnapshotDto[].class));

		// GET players before test request
		ResponseEntity<List<PlayerDto>> allPlayersResponseBefore = restTemplate.exchange(BASE_PLAYERS + ALL,
				HttpMethod.GET, null, new ParameterizedTypeReference<List<PlayerDto>>() {
				});
		List<PlayerDto> allPlayersBefore = allPlayersResponseBefore.getBody();

		// test POST request
		ResponseEntity<Void> postResponse = restTemplate.withBasicAuth("crawler", "password") //
				.postForEntity(BASE_FLAT_SNAPSHOTS + CREATE, flatPlayers, Void.class);

		// GET players after test request
		ResponseEntity<List<PlayerDto>> allPlayersResponseAfter = restTemplate.exchange(BASE_PLAYERS + ALL,
				HttpMethod.GET, null, new ParameterizedTypeReference<List<PlayerDto>>() {
				});
		List<PlayerDto> allPlayersAfter = allPlayersResponseAfter.getBody();

		// GET player snapshots by player ids after test request
		List<ResponseEntity<List<PlayerSnapshotDto>>> playerSnapshotResponses = allPlayersAfter //
				.stream() //
				.map(PlayerDto::getId) //
				.map(id -> {
					ResponseEntity<List<PlayerSnapshotDto>> playerSnapshotResponse = restTemplate.exchange(
							BASE_PLAYERS_SNAPSHOTS + "/" + id, HttpMethod.GET, null,
							new ParameterizedTypeReference<List<PlayerSnapshotDto>>() {
							});
					return playerSnapshotResponse;
				}) //
				.collect(Collectors.toList());

		SoftAssertions softAsserts = new SoftAssertions();

		// verify POST response
		softAsserts.assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		// verify player GET responses
		softAsserts.assertThat(allPlayersResponseBefore.getStatusCode()).isEqualTo(HttpStatus.OK);
		softAsserts.assertThat(allPlayersResponseAfter.getStatusCode()).isEqualTo(HttpStatus.OK);
		softAsserts.assertThat(allPlayersAfter).hasSize(allPlayersBefore.size() + 516);

		// player snapshot GET responses
		softAsserts.assertThat(playerSnapshotResponses) //
				.allMatch(response -> response.getStatusCode().equals(HttpStatus.OK)) //
				.hasSize(allPlayersBefore.size() + 516);

		softAsserts.assertAll();
	}
}
