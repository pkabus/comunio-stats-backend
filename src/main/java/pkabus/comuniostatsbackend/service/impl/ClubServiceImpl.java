package pkabus.comuniostatsbackend.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pkabus.comuniostatsbackend.persistence.model.ClubEntity;
import pkabus.comuniostatsbackend.persistence.repository.ClubRepository;
import pkabus.comuniostatsbackend.service.ClubService;

@Service
public class ClubServiceImpl implements ClubService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final ClubRepository clubRepo;

	public ClubServiceImpl(final ClubRepository clubRepo) {
		this.clubRepo = clubRepo;
	}

	@Override
	public Optional<ClubEntity> findById(final Long id) {
		return clubRepo.findById(id);
	}

	@Override
	public Page<ClubEntity> findAll(final Pageable page) {
		return clubRepo.findAll(page);
	}

	@Override
	public ClubEntity save(final ClubEntity club) {
		Optional<ClubEntity> clubByName = clubRepo.findByName(club.getName());

		if (clubByName.isPresent()) {
			logger.warn(String.format("Club '%s' is already present. Not created again.", clubByName.get().getName()));
		}

		return clubByName.orElseGet(() -> clubRepo.save(club));
	}

	@Override
	public Optional<ClubEntity> findByName(final String name) {
		return clubRepo.findByName(name);
	}

	@Override
	public void deleteAll() {
		clubRepo.deleteAll();
	}

	@Override
	public void delete(final ClubEntity entity) {
		clubRepo.delete(entity);
	}

	@Override
	public void deleteByName(final String name) {
		clubRepo.findByName(name).ifPresentOrElse(clubRepo::delete,
				() -> logger.info(String.format("Club '%s' not found. Cannot be deleted.", name)));
	}

}
