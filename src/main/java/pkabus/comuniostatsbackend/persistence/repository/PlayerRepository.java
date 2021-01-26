package pkabus.comuniostatsbackend.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pkabus.comuniostatsbackend.persistence.model.PlayerEntity;

public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {

	List<PlayerEntity> findByName(final String name);

	Optional<PlayerEntity> findByComunioId(final String comunioId);

}
