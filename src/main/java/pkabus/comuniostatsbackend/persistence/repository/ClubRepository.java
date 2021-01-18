package pkabus.comuniostatsbackend.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import pkabus.comuniostatsbackend.persistence.model.ClubEntity;

public interface ClubRepository extends PagingAndSortingRepository<ClubEntity, Long> {

	@Query("select c from ClubEntity c where c.name like %?1%")
	List<ClubEntity> findByNameMatches(String name);
	
}
