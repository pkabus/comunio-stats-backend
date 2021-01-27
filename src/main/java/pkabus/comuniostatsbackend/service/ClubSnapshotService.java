package pkabus.comuniostatsbackend.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pkabus.comuniostatsbackend.persistence.model.ClubSnapshotEntity;

public interface ClubSnapshotService {

	Optional<ClubSnapshotEntity> findById(Long id);

	Page<ClubSnapshotEntity> findByClubId(Long id, Pageable pageable);

	ClubSnapshotEntity save(ClubSnapshotEntity club);
}
