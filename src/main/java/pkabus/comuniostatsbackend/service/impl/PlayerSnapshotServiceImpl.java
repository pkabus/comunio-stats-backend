package pkabus.comuniostatsbackend.service.impl;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pkabus.comuniostatsbackend.persistence.model.PlayerSnapshotEntity;
import pkabus.comuniostatsbackend.persistence.repository.PlayerSnapshotRepository;
import pkabus.comuniostatsbackend.service.PlayerSnapshotService;

@Service
public class PlayerSnapshotServiceImpl implements PlayerSnapshotService {

	private PlayerSnapshotRepository playerSnapshotRepo;

	public PlayerSnapshotServiceImpl(final PlayerSnapshotRepository playerSnapshotRepository) {
		this.playerSnapshotRepo = playerSnapshotRepository;
	}

	@Override
	public Optional<PlayerSnapshotEntity> findById(Long id) {
		return playerSnapshotRepo.findById(id);
	}

	@Override
	public Page<PlayerSnapshotEntity> findByPlayerId(Long id, Pageable pageable) {
		return playerSnapshotRepo.findByPlayerId(id, pageable);
	}

	@Override
	public PlayerSnapshotEntity save(PlayerSnapshotEntity playerSnapshot) {
		return playerSnapshotRepo.save(playerSnapshot);
	}

	@Override
	public Page<PlayerSnapshotEntity> findByPlayerIdAndDateCreatedBetween(Long id, LocalDate start, LocalDate end,
			Pageable pageable) {
		return playerSnapshotRepo.findByPlayerIdAndDateCreatedBetween(id, start, end, pageable);
	}

}
