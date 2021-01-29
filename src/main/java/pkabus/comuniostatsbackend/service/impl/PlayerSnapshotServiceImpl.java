package pkabus.comuniostatsbackend.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pkabus.comuniostatsbackend.persistence.model.ClubEntity;
import pkabus.comuniostatsbackend.persistence.model.PlayerEntity;
import pkabus.comuniostatsbackend.persistence.model.PlayerSnapshotEntity;
import pkabus.comuniostatsbackend.persistence.repository.ClubRepository;
import pkabus.comuniostatsbackend.persistence.repository.PlayerRepository;
import pkabus.comuniostatsbackend.persistence.repository.PlayerSnapshotRepository;
import pkabus.comuniostatsbackend.service.PlayerSnapshotService;

@Service
public class PlayerSnapshotServiceImpl implements PlayerSnapshotService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final PlayerSnapshotRepository playerSnapshotRepo;

	private final PlayerRepository playerRepository;

	private final ClubRepository clubRepository;

	public PlayerSnapshotServiceImpl(final PlayerSnapshotRepository playerSnapshotRepository,
			final PlayerRepository playerRepository, final ClubRepository clubRepository) {
		this.playerSnapshotRepo = playerSnapshotRepository;
		this.playerRepository = playerRepository;
		this.clubRepository = clubRepository;
	}

	@Override
	public Optional<PlayerSnapshotEntity> findById(final Long id) {
		return playerSnapshotRepo.findById(id);
	}

	@Override
	public Page<PlayerSnapshotEntity> findByPlayerId(final Long id, final Pageable pageable) {
		return playerSnapshotRepo.findByPlayerId(id, pageable);
	}

	@Override
	public PlayerSnapshotEntity save(final PlayerSnapshotEntity playerSnapshot) {
		log.info("Save snapshot for " + playerSnapshot.getPlayer());
		return playerSnapshotRepo.save(playerSnapshot);
	}

	@Override
	public List<PlayerSnapshotEntity> saveAll(final Stream<PlayerSnapshotEntity> stream) {
		return stream //
				.map(this::addReferences) //
				.map(this::save) //
				.collect(Collectors.toList());
	}

	private PlayerSnapshotEntity addReferences(final PlayerSnapshotEntity playerSnapshotEntity) {
		PlayerEntity playerEntity = playerSnapshotEntity.getPlayer();
		PlayerEntity savedPlayerEntity = playerRepository.findByLink(playerEntity.getLink()).orElseGet(
				() -> playerRepository.save(new PlayerEntity(playerEntity.getName(), playerEntity.getLink())));

		ClubEntity clubEntity = playerSnapshotEntity.getClub();
		ClubEntity savedClubEntity = clubRepository.findByName(clubEntity.getName())
				.orElseGet(() -> clubRepository.save(new ClubEntity(clubEntity.getName())));

		playerSnapshotEntity.setClub(savedClubEntity);
		playerSnapshotEntity.setPlayer(savedPlayerEntity);
		return playerSnapshotEntity;
	}

	@Override
	public Page<PlayerSnapshotEntity> findByPlayerIdAndDateCreatedBetween(final Long id, final LocalDate start,
			final LocalDate end, final Pageable pageable) {
		return playerSnapshotRepo.findByPlayerIdAndCreatedBetween(id, start, end, pageable);
	}

	@Override
	public void deleteById(final Long id) {
		playerSnapshotRepo.deleteById(id);
	}

}
