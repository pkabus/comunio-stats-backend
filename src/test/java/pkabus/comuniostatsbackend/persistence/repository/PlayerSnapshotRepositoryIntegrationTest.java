package pkabus.comuniostatsbackend.persistence.repository;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import pkabus.comuniostatsbackend.persistence.model.PlayerEntity;
import pkabus.comuniostatsbackend.persistence.model.PlayerSnapshotEntity;

@SpringBootTest
public class PlayerSnapshotRepositoryIntegrationTest {

	@Autowired
	private PlayerSnapshotRepository playerSnapshotRepository;

	@Autowired
	private PlayerRepository playerRepository;

	@Test
	void givenPlayerSnapshot_whenFindByPlayerId_thenSuccess() {
		PlayerEntity playerEntity = new PlayerEntity(randomAlphabetic(6), randomAlphabetic(6));
		playerRepository.save(playerEntity);

		PlayerSnapshotEntity playerSnapshotEntity = new PlayerSnapshotEntity(playerEntity, Long.valueOf(160000),
				Integer.valueOf(0), LocalDate.now(), randomAlphabetic(6));

		playerSnapshotRepository.save(playerSnapshotEntity);

		Page<PlayerSnapshotEntity> retrievedPlayerSnapshots = playerSnapshotRepository
				.findByPlayerId(playerEntity.getId(), PageRequest.of(0, 1));

		assertThat(retrievedPlayerSnapshots).containsExactly(playerSnapshotEntity);
	}
}
