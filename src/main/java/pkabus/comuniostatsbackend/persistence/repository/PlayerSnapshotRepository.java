package pkabus.comuniostatsbackend.persistence.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import pkabus.comuniostatsbackend.persistence.model.PlayerSnapshotEntity;

public interface PlayerSnapshotRepository extends JpaRepository<PlayerSnapshotEntity, Long> {

	Page<PlayerSnapshotEntity> findByPlayerId(Long playerId, Pageable pageable);

	Page<PlayerSnapshotEntity> findByCreated(LocalDate date, Pageable pageable);

	Page<PlayerSnapshotEntity> findByCreatedBetween(LocalDate start, LocalDate end, Pageable pageable);

	Page<PlayerSnapshotEntity> findByPlayerIdAndCreatedBetween(Long id, LocalDate start, LocalDate end,
			Pageable pageable);

	Page<PlayerSnapshotEntity> findByClubNameAndCreated(String name, LocalDate date, Pageable page);

	Optional<PlayerSnapshotEntity> findByPlayerLinkAndCreated(String name, LocalDate date);
}
