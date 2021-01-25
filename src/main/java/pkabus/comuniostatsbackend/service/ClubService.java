package pkabus.comuniostatsbackend.service;

import java.util.Optional;

import pkabus.comuniostatsbackend.persistence.model.ClubEntity;

public interface ClubService {

	Optional<ClubEntity> findById(final Long id);

	ClubEntity save(final ClubEntity club);
}
