package pkabus.comuniostatsbackend.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pkabus.comuniostatsbackend.persistence.model.PlayerSnapshotEntity;

public interface PlayerSnapshotService {

	Optional<PlayerSnapshotEntity> findById(Long id);

	Page<PlayerSnapshotEntity> findByPlayerId(Long id, Pageable pageable);

	Page<PlayerSnapshotEntity> findByPlayerIdAndDateCreatedBetween(Long id, LocalDate start, LocalDate end,
			Pageable pageable);

	PlayerSnapshotEntity save(PlayerSnapshotEntity playerSnapshot);

	void deleteById(Long id);

	List<PlayerSnapshotEntity> saveAll(Stream<PlayerSnapshotEntity> map);

}
