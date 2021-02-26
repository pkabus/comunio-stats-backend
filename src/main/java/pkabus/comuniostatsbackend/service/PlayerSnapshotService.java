package pkabus.comuniostatsbackend.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pkabus.comuniostatsbackend.persistence.model.PlayerSnapshotEntity;

public interface PlayerSnapshotService {

	Optional<PlayerSnapshotEntity> findById(Long id);

	Page<PlayerSnapshotEntity> findByPlayerId(Long id, Pageable pageable);

	Page<PlayerSnapshotEntity> findByPlayerIdAndCreatedBetween(Long id, LocalDate start, LocalDate end,
			Pageable pageable);

	Page<PlayerSnapshotEntity> findByClubNameAndCreated(String name, LocalDate date, Pageable page);

	void deleteById(Long id);
}
