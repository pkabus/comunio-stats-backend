package pkabus.comuniostatsbackend.web.controller;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import pkabus.comuniostatsbackend.web.dto.ClubDto;
import pkabus.comuniostatsbackend.web.dto.FlatPlayerSnapshotDto;
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

	@Autowired
	private FlatPlayerSnapshotController flatPlayerSnapshotController;

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

		FlatPlayerSnapshotDto playerSnapshotDto = new FlatPlayerSnapshotDto(playerDtoByLink.getName(),
				playerDto.getLink(), new Random().nextLong(), randomAlphabetic(6), new Random().nextInt(),
				savedClubDto.getName(), new Random().nextLong(), LocalDate.now());
		flatPlayerSnapshotController.add(playerSnapshotDto);
		List<PlayerSnapshotDto> byPlayerId = playerSnapshotController.byPlayerId(playerDtoByLink.getId());

		assertThat(byPlayerId).usingElementComparatorIgnoringFields("id", "club", "player")
				.containsExactly(flatSnapshotToSnapshot(playerSnapshotDto));
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
		FlatPlayerSnapshotDto playerOneSnapshotDto = new FlatPlayerSnapshotDto(savedPlayerOneDto.getName(),
				savedPlayerOneDto.getLink(), new Random().nextLong(), randomAlphabetic(6), new Random().nextInt(),
				savedClubDto.getName(), new Random().nextLong(), LocalDate.now().minusDays(1));
		FlatPlayerSnapshotDto playerOneNewerSnapshotDto = new FlatPlayerSnapshotDto(savedPlayerOneDto.getName(),
				savedPlayerOneDto.getLink(), new Random().nextLong(), randomAlphabetic(6), new Random().nextInt(),
				savedClubDto.getName(), new Random().nextLong(), LocalDate.now());
		flatPlayerSnapshotController.add(Arrays.array(playerOneSnapshotDto, playerOneNewerSnapshotDto));

		// create another player + snapshot
		String playerTwoName = randomAlphabetic(6);
		String playerTwoLink = randomAlphabetic(6);
		PlayerDto playerTwoDto = new PlayerDto(playerTwoName, playerTwoLink);
		playerController.create(playerTwoDto);
		PlayerDto savedPlayerTwoDto = playerController.byName(playerTwoName).get(0);

		FlatPlayerSnapshotDto playerTwoSnapshotDto = new FlatPlayerSnapshotDto(savedPlayerTwoDto.getName(),
				savedPlayerTwoDto.getLink(), new Random().nextLong(), randomAlphabetic(6), new Random().nextInt(),
				savedClubDto.getName(), new Random().nextLong(), LocalDate.now().minusDays(1));
		flatPlayerSnapshotController.add(playerTwoSnapshotDto);

		List<PlayerSnapshotDto> distinctByClubName = playerSnapshotController.byClubName(clubDto.getName());

		assertThat(distinctByClubName) //
				.usingElementComparatorIgnoringFields("id", "club", "player") //
				.containsExactlyInAnyOrder(flatSnapshotToSnapshot(playerOneSnapshotDto),
						flatSnapshotToSnapshot(playerTwoSnapshotDto));
	}

	private PlayerSnapshotDto flatSnapshotToSnapshot(final FlatPlayerSnapshotDto flatPlayerSnapshot) {
		PlayerDto playerDto = new PlayerDto(flatPlayerSnapshot.getName(), flatPlayerSnapshot.getLink());
		ClubDto clubDto = new ClubDto(flatPlayerSnapshot.getClub());
		return new PlayerSnapshotDto(playerDto, clubDto, flatPlayerSnapshot.getMarketValue(),
				flatPlayerSnapshot.getPointsDuringCurrentSeason(), flatPlayerSnapshot.getCreated(),
				flatPlayerSnapshot.getPosition());
	}
}
