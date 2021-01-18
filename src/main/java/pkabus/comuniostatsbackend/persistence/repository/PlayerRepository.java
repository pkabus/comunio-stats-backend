package pkabus.comuniostatsbackend.persistence.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import pkabus.comuniostatsbackend.persistence.model.PlayerEntity;

public interface PlayerRepository extends PagingAndSortingRepository<PlayerEntity, Long> {

	List<PlayerEntity> findByName(String name);

}
