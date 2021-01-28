package pkabus.comuniostatsbackend.web.client;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import pkabus.comuniostatsbackend.web.controller.PlayerController;
import pkabus.comuniostatsbackend.web.controller.PlayerSnapshotController;
import pkabus.comuniostatsbackend.web.dto.PlayerDto;
import pkabus.comuniostatsbackend.web.dto.PlayerSnapshotDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PlayerSnapshotRestApiTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void givenPlayerSnapshots_whenByPlayerId_then200Ok() {
		ResponseEntity<List<PlayerDto>> responseList = restTemplate.exchange(
				PlayerController.BASE_PLAYERS + PlayerController.ALL, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<PlayerDto>>() {
				});
		PlayerDto playerDto = responseList.getBody().get(0);

		ResponseEntity<List<PlayerSnapshotDto>> response = restTemplate.exchange(
				PlayerSnapshotController.BASE_PLAYERS_SNAPSHOTS + "/" + playerDto.getId(), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<PlayerSnapshotDto>>() {
				});

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody()).hasSize(4) //
				.allMatch(dto -> dto.getMarketValue() == 160000);
	}
}
