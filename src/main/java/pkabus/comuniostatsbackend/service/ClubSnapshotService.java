package pkabus.comuniostatsbackend.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pkabus.comuniostatsbackend.persistence.model.ClubSnapshotEntity;

public interface ClubSnapshotService {

	Optional<ClubSnapshotEntity> findById(final Long id);

	Page<ClubSnapshotEntity> findByClubId(final Long id, final Pageable pageable);

	ClubSnapshotEntity save(final ClubSnapshotEntity club);
}
