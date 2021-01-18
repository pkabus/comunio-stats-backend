package pkabus.comuniostatsbackend.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import pkabus.comuniostatsbackend.persistence.model.PlayerEntity;
import pkabus.comuniostatsbackend.persistence.repository.PlayerRepository;
import pkabus.comuniostatsbackend.service.PlayerService;

@Service
public class PlayerServiceImpl implements PlayerService {
	
	private PlayerRepository playerRepo;
	
	public PlayerServiceImpl(PlayerRepository playerRepository) {
		this.playerRepo = playerRepository;
	}
	
	public Optional<PlayerEntity> findById(final Long id) {
		return playerRepo.findById(id);
	}

	public PlayerEntity save(final PlayerEntity player) {
		return playerRepo.save(player);
	}

}
