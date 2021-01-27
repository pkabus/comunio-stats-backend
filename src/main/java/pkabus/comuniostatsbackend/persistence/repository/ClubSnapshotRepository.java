package pkabus.comuniostatsbackend.persistence.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import pkabus.comuniostatsbackend.persistence.model.ClubSnapshotEntity;

public interface ClubSnapshotRepository extends JpaRepository<ClubSnapshotEntity, Long> {

	Page<ClubSnapshotEntity> findByClubId(final Long clubId, final Pageable pageable);

	Page<ClubSnapshotEntity> findByCreated(final LocalDate date, final Pageable pageable);

	Page<ClubSnapshotEntity> findByCreatedBetween(final LocalDate start, final LocalDate end,
			final Pageable pageable);
}
