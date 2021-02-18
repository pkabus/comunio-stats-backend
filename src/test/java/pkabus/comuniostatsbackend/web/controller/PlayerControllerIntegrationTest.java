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
		ClubDto savedClubDto = clubController.byName(clubDto.getName());

		PlayerDto playerDtoByLink = playerController.byLink(link);

		PlayerSnapshotDto playerSnapshotDto = new PlayerSnapshotDto(playerDtoByLink, savedClubDto,
				new Random().nextLong(), new Random().nextInt(), LocalDate.now(), randomAlphabetic(6));
		playerSnapshotController.add(playerSnapshotDto);
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

		playerController.deleteByLink(link);

		assertThatThrownBy(() -> playerController.byLink(link)) //
				.isInstanceOf(ResponseStatusException.class);
	}

	@Test
	void givenClubAndPlayers_whenFindByClubName_thenReturnPlayers() {
		ClubDto clubDto = new ClubDto(randomAlphabetic(6));
		clubController.create(clubDto);
		ClubDto savedClubDto = clubController.byName(clubDto.getName());

		String playerOneName = randomAlphabetic(6);
		String playerOneLink = randomAlphabetic(6);
		PlayerDto playerOneDto = new PlayerDto(playerOneName, playerOneLink);
		playerController.create(playerOneDto);
		PlayerDto savedPlayerOneDto = playerController.byName(playerOneName).get(0);

		// create two snapshots for the same player
		PlayerSnapshotDto playerOneSnapshotDto = new PlayerSnapshotDto(savedPlayerOneDto, savedClubDto,
				new Random().nextLong(), new Random().nextInt(), LocalDate.now().minusDays(1), randomAlphabetic(6));
		playerSnapshotController.add(playerOneSnapshotDto);
		PlayerSnapshotDto playerOneNewerSnapshotDto = new PlayerSnapshotDto(savedPlayerOneDto, savedClubDto,
				new Random().nextLong(), new Random().nextInt(), LocalDate.now(), randomAlphabetic(6));
		playerSnapshotController.add(playerOneNewerSnapshotDto);

		// create another player + snapshot
		String playerTwoName = randomAlphabetic(6);
		String playerTwoLink = randomAlphabetic(6);
		PlayerDto playerTwoDto = new PlayerDto(playerTwoName, playerTwoLink);
		playerController.create(playerTwoDto);
		PlayerDto savedPlayerTwoDto = playerController.byName(playerTwoName).get(0);

		PlayerSnapshotDto playerTwoSnapshotDto = new PlayerSnapshotDto(savedPlayerTwoDto, savedClubDto,
				new Random().nextLong(), new Random().nextInt(), LocalDate.now().minusDays(1), randomAlphabetic(6));
		playerSnapshotController.add(playerTwoSnapshotDto);

		List<PlayerSnapshotDto> distinctByClubName = playerSnapshotController.byClubName(clubDto.getName());

		assertThat(distinctByClubName) //
				.usingElementComparatorIgnoringFields("id") //
				.containsExactlyInAnyOrder(playerOneSnapshotDto, playerTwoSnapshotDto);
	}
}
