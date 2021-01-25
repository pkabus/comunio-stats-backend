package pkabus.comuniostatsbackend.persistence.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import pkabus.comuniostatsbackend.persistence.model.ClubSnapshotEntity;

public interface ClubSnapshotRepository extends PagingAndSortingRepository<ClubSnapshotEntity, Long> {

	Page<ClubSnapshotEntity> findByClubId(final Long clubId, final Pageable pageable);

	Page<ClubSnapshotEntity> findByDateCreated(final LocalDate date, final Pageable pageable);

	Page<ClubSnapshotEntity> findByDateCreatedBetween(final LocalDate start, final LocalDate end,
			final Pageable pageable);
}
