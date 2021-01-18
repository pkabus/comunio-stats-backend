package pkabus.comuniostatsbackend.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import pkabus.comuniostatsbackend.persistence.model.ClubEntity;
import pkabus.comuniostatsbackend.persistence.repository.ClubRepository;
import pkabus.comuniostatsbackend.service.ClubService;

@Service
public class ClubServiceImpl implements ClubService {

	private ClubRepository clubRepo;

	public ClubServiceImpl(ClubRepository clubRepo) {
		this.clubRepo = clubRepo;
	}

	public Optional<ClubEntity> findById(final Long id) {
		return clubRepo.findById(id);
	}

	public ClubEntity save(final ClubEntity club) {
		return clubRepo.save(club);
	}

}
