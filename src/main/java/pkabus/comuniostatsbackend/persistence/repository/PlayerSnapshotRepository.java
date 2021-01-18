package pkabus.comuniostatsbackend.persistence.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import pkabus.comuniostatsbackend.persistence.model.PlayerSnapshotEntity;

public interface PlayerSnapshotRepository extends PagingAndSortingRepository<PlayerSnapshotEntity, Long> {

	Page<PlayerSnapshotEntity> findByPlayerId(Long playerId, Pageable pageable);

	List<PlayerSnapshotEntity> findByDateCreated(LocalDate date);

	List<PlayerSnapshotEntity> findByDateCreatedBetween(LocalDate start, LocalDate end);
}
