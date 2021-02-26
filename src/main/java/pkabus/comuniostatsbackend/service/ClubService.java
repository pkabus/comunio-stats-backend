package pkabus.comuniostatsbackend.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pkabus.comuniostatsbackend.persistence.model.ClubEntity;

public interface ClubService {

	Page<ClubEntity> findAll(Pageable page);

	Optional<ClubEntity> findById(Long id);

	ClubEntity save(ClubEntity club);

	Optional<ClubEntity> findByName(String name);

	void deleteAll();

	void delete(ClubEntity entity);

	void deleteByName(String name);
}
