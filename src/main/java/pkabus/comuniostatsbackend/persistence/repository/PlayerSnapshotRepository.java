package pkabus.comuniostatsbackend.persistence.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import pkabus.comuniostatsbackend.persistence.model.PlayerSnapshotEntity;

public interface PlayerSnapshotRepository extends JpaRepository<PlayerSnapshotEntity, Long> {

	Page<PlayerSnapshotEntity> findByPlayerId(final Long playerId, final Pageable pageable);

	Page<PlayerSnapshotEntity> findByDateCreated(final LocalDate date, final Pageable pageable);

	Page<PlayerSnapshotEntity> findByDateCreatedBetween(final LocalDate start, final LocalDate end,
			final Pageable pageable);

	Page<PlayerSnapshotEntity> findByPlayerIdAndDateCreatedBetween(final Long id, final LocalDate start,
			final LocalDate end, Pageable pageable);
}
