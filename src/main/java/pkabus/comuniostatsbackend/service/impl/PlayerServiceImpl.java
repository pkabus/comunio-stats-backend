package pkabus.comuniostatsbackend.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import pkabus.comuniostatsbackend.persistence.model.PlayerEntity;
import pkabus.comuniostatsbackend.persistence.repository.PlayerRepository;
import pkabus.comuniostatsbackend.service.PlayerService;

@Service
public class PlayerServiceImpl implements PlayerService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private PlayerRepository playerRepo;

	public PlayerServiceImpl(final PlayerRepository playerRepository) {
		this.playerRepo = playerRepository;
	}

	@Override
	public Iterable<PlayerEntity> findAll() {
		return playerRepo.findAll();
	}

	@Override
	public Optional<PlayerEntity> findById(final Long id) {
		return playerRepo.findById(id);
	}

	@Override
	public PlayerEntity save(final PlayerEntity player) {
		Optional<PlayerEntity> playerByComunioId = playerRepo.findByLink(player.getComunioId());

		if (playerByComunioId.isPresent()) {
			logger.warn(String.format("Player '%s' with unique link '%s' is already present. Not created again.",
					playerByComunioId.get().getName(), playerByComunioId.get().getComunioId()));
		}

		return playerByComunioId.orElseGet(() -> playerRepo.save(player));
	}

	@Override
	public void delete(final PlayerEntity player) {
		playerRepo.delete(player);
	}

	@Override
	public void deleteAll() {
		playerRepo.deleteAll();
	}

	@Override
	public void deleteByLink(final String link) {
		playerRepo.findByLink(link).ifPresentOrElse(playerRepo::delete, () -> logger
				.info(String.format("Player with comunioId '%s' not found. Cannot be deleted.", link)));
	}

	@Override
	public List<PlayerEntity> findByName(final String name) {
		return playerRepo.findByName(name);
	}

	@Override
	public Optional<PlayerEntity> findByLink(final String link) {
		return playerRepo.findByLink(link);
	}

}
