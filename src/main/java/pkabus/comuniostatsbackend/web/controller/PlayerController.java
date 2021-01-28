package pkabus.comuniostatsbackend.web.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import pkabus.comuniostatsbackend.persistence.model.PlayerEntity;
import pkabus.comuniostatsbackend.service.PlayerService;
import pkabus.comuniostatsbackend.web.dto.PlayerDto;

@RestController
@RequestMapping(PlayerController.BASE_PLAYERS)
public class PlayerController {

	public static final String BASE_PLAYERS = "/players";
	public static final String ALL = "/all";
	public static final String CREATE = "/create";
	public static final String DELETE = "/delete";

	private final PlayerService playerService;

	private final ModelMapper modelMapper;

	public PlayerController(final PlayerService playerService, final ModelMapper modelMapper) {
		super();
		this.playerService = playerService;
		this.modelMapper = modelMapper;
	}

	@GetMapping(ALL)
	public List<PlayerDto> all() {
		return StreamSupport.stream(playerService.findAll().spliterator(), false) //
				.map(this::toDto) //
				.collect(Collectors.toList());
	}

	@GetMapping(value = "/{id}")
	public PlayerDto byId(@PathVariable final Long id) {
		PlayerEntity playerEntity = playerService.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return toDto(playerEntity);
	}

	@GetMapping(params = "name")
	public List<PlayerDto> byName(@RequestParam final String name) {
		return playerService.findByName(name) //
				.stream() //
				.map(this::toDto) //
				.collect(Collectors.toList());
	}

	@GetMapping(params = "link")
	public PlayerDto byLink(@RequestParam final String link) {
		PlayerEntity playerEntity = playerService.findByLink(link)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return toDto(playerEntity);
	}

	@PostMapping(CREATE)
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@RequestBody final PlayerDto player) {
		playerService.save(toEntity(player));
	}

	@DeleteMapping(value = DELETE, params = "link")
	public void deleteByLink(@RequestParam final String link) {
		playerService.deleteByLink(link);
	}

	public void delete(final PlayerDto player) {
		playerService.delete(toEntity(player));
	}

	public void deleteAll() {
		playerService.deleteAll();
	}

	private PlayerDto toDto(final PlayerEntity playerEntity) {
		return modelMapper.map(playerEntity, PlayerDto.class);
	}

	private PlayerEntity toEntity(final PlayerDto playerDto) {
		return modelMapper.map(playerDto, PlayerEntity.class);
	}

}
