package pkabus.comuniostatsbackend.web.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import pkabus.comuniostatsbackend.web.dto.PlayerDto;
import pkabus.comuniostatsbackend.web.dto.PlayerSnapshotDto;

@SpringBootTest
public class PlayerControllerIntegrationTest {

	@Autowired
	private PlayerController playerController;

	@Autowired
	private PlayerSnapshotController playerSnapshotController;

	@Test
	@Order(0)
	void whenCreatePlayerAndIgnoreId_thenSuccess() {
		String name = randomAlphabetic(6);
		String comunioId = randomAlphabetic(6);
		PlayerDto playerDto = new PlayerDto(name, comunioId);
		playerController.create(playerDto);
		List<PlayerDto> byName = playerController.byName(name);

		assertThat(byName).usingElementComparatorIgnoringFields("id").containsExactly(playerDto);
	}

	@Test
	@Order(1)
	void givenPlayer_whenAddSnapshot_thenSuccess() {
		String name = randomAlphabetic(6);
		String comunioId = randomAlphabetic(6);
		PlayerDto playerDto = new PlayerDto(name, comunioId);
		playerController.create(playerDto);
		PlayerDto playerDtoByComunioId = playerController.byComunioId(comunioId);

		PlayerSnapshotDto playerSnapshotDto = new PlayerSnapshotDto(playerDtoByComunioId, new Random().nextLong(),
				new Random().nextInt(), LocalDate.now(), randomAlphabetic(6));
		playerSnapshotController.addSnapshot(playerSnapshotDto);
		List<PlayerSnapshotDto> byPlayerId = playerSnapshotController.byPlayerId(playerDtoByComunioId.getId());

		assertThat(byPlayerId).usingElementComparatorIgnoringFields("id").containsExactly(playerSnapshotDto);
	}
}
