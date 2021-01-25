package pkabus.comuniostatsbackend.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pkabus.comuniostatsbackend.persistence.model.PlayerSnapshotEntity;

public interface PlayerSnapshotService {

	Optional<PlayerSnapshotEntity> findById(final Long id);

	Page<PlayerSnapshotEntity> findByPlayerId(final Long id, Pageable pageable);

	Page<PlayerSnapshotEntity> findByPlayerIdAndDateCreatedBetween(final Long id, final LocalDate start,
			final LocalDate end, Pageable pageable);

	PlayerSnapshotEntity save(final PlayerSnapshotEntity playerSnapshot);

}
