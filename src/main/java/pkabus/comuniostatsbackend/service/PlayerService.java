package pkabus.comuniostatsbackend.service;

import java.util.List;
import java.util.Optional;

import pkabus.comuniostatsbackend.persistence.model.PlayerEntity;

public interface PlayerService {

	Optional<PlayerEntity> findById(Long id);

	PlayerEntity save(PlayerEntity club);

	List<PlayerEntity> findByName(String name);

	Optional<PlayerEntity> findByComunioId(String comunioId);

	Iterable<PlayerEntity> findAll();

	void delete(PlayerEntity player);

	void deleteAll();
}
