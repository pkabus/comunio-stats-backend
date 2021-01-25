package pkabus.comuniostatsbackend.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pkabus.comuniostatsbackend.persistence.model.ClubSnapshotEntity;
import pkabus.comuniostatsbackend.persistence.repository.ClubSnapshotRepository;
import pkabus.comuniostatsbackend.service.ClubSnapshotService;

@Service
public class ClubSnapshotServiceImpl implements ClubSnapshotService {

	private ClubSnapshotRepository clubSnapshotRepo;

	public ClubSnapshotServiceImpl(ClubSnapshotRepository clubSnapshotRepository) {
		this.clubSnapshotRepo = clubSnapshotRepository;
	}

	@Override
	public Optional<ClubSnapshotEntity> findById(Long id) {
		return clubSnapshotRepo.findById(id);
	}

	@Override
	public Page<ClubSnapshotEntity> findByClubId(Long id, Pageable pageable) {
		return clubSnapshotRepo.findByClubId(id, pageable);
	}

	@Override
	public ClubSnapshotEntity save(ClubSnapshotEntity clubSnapshot) {
		return clubSnapshotRepo.save(clubSnapshot);
	}

}
