package pkabus.comuniostatsbackend.web.client;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
import static pkabus.comuniostatsbackend.web.controller.PlayerController.ALL;
import static pkabus.comuniostatsbackend.web.controller.PlayerController.BASE_PLAYERS;
import static pkabus.comuniostatsbackend.web.controller.PlayerController.CREATE;

import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import pkabus.comuniostatsbackend.web.dto.PlayerDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PlayerRestApiTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void givenPlayer_whenAll_then200Ok() {
		ResponseEntity<List<PlayerDto>> response = restTemplate.exchange(BASE_PLAYERS + ALL, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<PlayerDto>>() {
				});

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
	}

	@Test
	void whenCreate_then201Created() {
		PlayerDto playerDto = new PlayerDto(randomAlphabetic(6), randomAlphabetic(6));
		ResponseEntity<Void> response = restTemplate.withBasicAuth("crawler", "password")
				.postForEntity(BASE_PLAYERS + CREATE, playerDto, Void.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}

	@Test
	void whenUnknownId_then404NotFound() {
		ResponseEntity<?> responseUnknown = restTemplate.getForEntity(BASE_PLAYERS + "/" + new Random().nextLong(),
				Object.class);

		assertThat(responseUnknown.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	void givenPlayer_whenKnownId_then200Ok() {
		ResponseEntity<List<PlayerDto>> responseList = restTemplate.exchange(BASE_PLAYERS + ALL, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<PlayerDto>>() {
				});

		PlayerDto playerDto = responseList.getBody().get(0);

		ResponseEntity<PlayerDto> response = restTemplate.getForEntity(BASE_PLAYERS + "/" + playerDto.getId(),
				PlayerDto.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
}
