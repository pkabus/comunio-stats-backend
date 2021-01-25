package pkabus.comuniostatsbackend.service;

import java.util.List;
import java.util.Optional;

import pkabus.comuniostatsbackend.persistence.model.PlayerEntity;

public interface PlayerService {

	Optional<PlayerEntity> findById(final Long id);

	PlayerEntity save(final PlayerEntity club);

	List<PlayerEntity> findByName(final String name);

	Optional<PlayerEntity> findByComunioId(final String comunioId);
}
