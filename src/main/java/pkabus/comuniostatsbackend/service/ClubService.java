package pkabus.comuniostatsbackend.service;

import java.util.Optional;

import pkabus.comuniostatsbackend.persistence.model.ClubEntity;

public interface ClubService {

	Iterable<ClubEntity> findAll();

	Optional<ClubEntity> findById(Long id);

	ClubEntity save(ClubEntity club);

	Optional<ClubEntity> findByName(String name);

	void deleteAll();

	void delete(ClubEntity entity);
}
