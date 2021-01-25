package pkabus.comuniostatsbackend.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import pkabus.comuniostatsbackend.persistence.model.PlayerEntity;
import pkabus.comuniostatsbackend.persistence.repository.PlayerRepository;
import pkabus.comuniostatsbackend.service.PlayerService;

@Service
public class PlayerServiceImpl implements PlayerService {

	private PlayerRepository playerRepo;

	public PlayerServiceImpl(final PlayerRepository playerRepository) {
		this.playerRepo = playerRepository;
	}

	public Optional<PlayerEntity> findById(final Long id) {
		return playerRepo.findById(id);
	}

	public PlayerEntity save(final PlayerEntity player) {
		return playerRepo.save(player);
	}

	@Override
	public List<PlayerEntity> findByName(final String name) {
		return playerRepo.findByName(name);
	}

	@Override
	public Optional<PlayerEntity> findByComunioId(final String comunioId) {
		return playerRepo.findByComunioId(comunioId);
	}

}
