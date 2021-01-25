package pkabus.comuniostatsbackend.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import pkabus.comuniostatsbackend.persistence.model.PlayerEntity;
import pkabus.comuniostatsbackend.service.PlayerService;
import pkabus.comuniostatsbackend.web.dto.PlayerDto;

@RestController
@RequestMapping("/players")
public class PlayerController {

	private PlayerService playerService;

	private ModelMapper modelMapper;

	public PlayerController(final PlayerService playerService, final ModelMapper modelMapper) {
		super();
		this.playerService = playerService;
		this.modelMapper = modelMapper;
	}

	@GetMapping(value = "/{id}")
	public PlayerDto byId(@PathVariable final Long id) {
		PlayerEntity playerEntity = playerService.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return toDto(playerEntity);
	}

	@GetMapping("/byName={name}")
	public List<PlayerDto> byName(@PathVariable final String name) {
		return playerService.findByName(name) //
				.stream() //
				.map(this::toDto) //
				.collect(Collectors.toList());
	}

	@GetMapping("/byComunioId={comunioId}")
	public PlayerDto byComunioId(@PathVariable final String comunioId) {
		PlayerEntity playerEntity = playerService.findByComunioId(comunioId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return toDto(playerEntity);
	}

	@PostMapping
	public void create(@RequestBody final PlayerDto player) {
		playerService.save(toEntity(player));
	}

	private PlayerDto toDto(final PlayerEntity playerEntity) {
		return modelMapper.map(playerEntity, PlayerDto.class);
	}

	private PlayerEntity toEntity(final PlayerDto playerDto) {
		return modelMapper.map(playerDto, PlayerEntity.class);
	}
}
