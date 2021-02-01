package pkabus.comuniostatsbackend.service.impl;

import java.time.LocalDate;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pkabus.comuniostatsbackend.persistence.model.PlayerSnapshotEntity;
import pkabus.comuniostatsbackend.persistence.repository.PlayerSnapshotRepository;
import pkabus.comuniostatsbackend.service.PlayerSnapshotService;

@Service
public class PlayerSnapshotServiceImpl implements PlayerSnapshotService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final PlayerSnapshotRepository playerSnapshotRepo;

	public PlayerSnapshotServiceImpl(final PlayerSnapshotRepository playerSnapshotRepository) {
		this.playerSnapshotRepo = playerSnapshotRepository;
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
	public Page<PlayerSnapshotEntity> findByPlayerIdAndDateCreatedBetween(final Long id, final LocalDate start,
			final LocalDate end, final Pageable pageable) {
		return playerSnapshotRepo.findByPlayerIdAndCreatedBetween(id, start, end, pageable);
	}

	@Override
	public void deleteById(final Long id) {
		playerSnapshotRepo.deleteById(id);
	}

}
