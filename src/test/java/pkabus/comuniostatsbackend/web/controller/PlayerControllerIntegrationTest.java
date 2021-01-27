package pkabus.comuniostatsbackend.web.controller;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import pkabus.comuniostatsbackend.web.dto.ClubDto;
import pkabus.comuniostatsbackend.web.dto.PlayerDto;
import pkabus.comuniostatsbackend.web.dto.PlayerSnapshotDto;

@SpringBootTest
public class PlayerControllerIntegrationTest {

	@Autowired
	private PlayerController playerController;

	@Autowired
	private ClubController clubController;

	@Autowired
	private PlayerSnapshotController playerSnapshotController;

	@Test
	void whenCreatePlayerAndIgnoreId_thenSuccess() {
		String name = randomAlphabetic(6);
		String link = randomAlphabetic(6);
		PlayerDto playerDto = new PlayerDto(name, link);
		playerController.create(playerDto);
		List<PlayerDto> byName = playerController.byName(name);

		assertThat(byName).usingElementComparatorIgnoringFields("id").containsExactly(playerDto);
	}

	@Test
	void givenPlayer_whenAddSnapshot_thenSuccess() {
		String name = randomAlphabetic(6);
		String link = randomAlphabetic(6);
		PlayerDto playerDto = new PlayerDto(name, link);
		playerController.create(playerDto);

		ClubDto clubDto = new ClubDto(randomAlphabetic(6));
		clubController.create(clubDto);

		PlayerDto playerDtoByLink = playerController.byLink(link);

		PlayerSnapshotDto playerSnapshotDto = new PlayerSnapshotDto(playerDtoByLink, clubDto, new Random().nextLong(),
				new Random().nextInt(), LocalDate.now(), randomAlphabetic(6));
		playerSnapshotController.addSnapshot(playerSnapshotDto);
		List<PlayerSnapshotDto> byPlayerId = playerSnapshotController.byPlayerId(playerDtoByLink.getId());

		assertThat(byPlayerId).usingElementComparatorIgnoringFields("id").containsExactly(playerSnapshotDto);
	}

	@Test
	void givenPlayer_deleteByComunioId_thenSuccess() {
		String name = randomAlphabetic(6);
		String link = randomAlphabetic(6);
		PlayerDto playerDto = new PlayerDto(name, link);
		playerController.create(playerDto);

		PlayerDto byComunioIdBefore = playerController.byLink(link);
		assertThat(byComunioIdBefore).usingRecursiveComparison() //
				.ignoringFields("id").isEqualTo(playerDto);

		playerController.deleteByComunioId(link);

		assertThatThrownBy(() -> playerController.byLink(link)) //
				.isInstanceOf(ResponseStatusException.class);
	}
}
