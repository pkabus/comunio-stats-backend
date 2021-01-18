package pkabus.comuniostatsbackend;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.time.LocalDate;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import pkabus.comuniostatsbackend.persistence.model.PlayerEntity;
import pkabus.comuniostatsbackend.persistence.model.PlayerSnapshotEntity;
import pkabus.comuniostatsbackend.persistence.repository.PlayerRepository;
import pkabus.comuniostatsbackend.persistence.repository.PlayerSnapshotRepository;

@Component
public class RepositoryTestDataLoader implements ApplicationContextAware {

	static final String LINK_FROM_COMUNIO = "link/from/comunio";

	static final String TEST_PLAYER_000 = "testPlayer_000";

	@Autowired
	private PlayerRepository playerRepository;

	@Autowired
	private PlayerSnapshotRepository playerSnapshotRepository;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		PlayerEntity playerEntity = new PlayerEntity(TEST_PLAYER_000, LINK_FROM_COMUNIO);
		playerRepository.save(playerEntity);

		playerSnapshotRepository.save(new PlayerSnapshotEntity(playerEntity, Long.valueOf(160000), Integer.valueOf(0),
				LocalDate.now(), randomAlphabetic(6)));

		playerSnapshotRepository.save(new PlayerSnapshotEntity(playerEntity, Long.valueOf(160000), Integer.valueOf(0),
				LocalDate.now(), randomAlphabetic(6)));

		playerSnapshotRepository.save(new PlayerSnapshotEntity(playerEntity, Long.valueOf(160000), Integer.valueOf(0),
				LocalDate.now(), randomAlphabetic(6)));

		playerSnapshotRepository.save(new PlayerSnapshotEntity(playerEntity, Long.valueOf(160000), Integer.valueOf(0),
				LocalDate.now(), randomAlphabetic(6)));
	}
}
