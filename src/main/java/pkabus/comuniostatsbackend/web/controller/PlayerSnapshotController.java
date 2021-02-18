package pkabus.comuniostatsbackend.web.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import pkabus.comuniostatsbackend.persistence.model.PlayerSnapshotEntity;
import pkabus.comuniostatsbackend.service.PlayerSnapshotService;
import pkabus.comuniostatsbackend.web.dto.PlayerSnapshotDto;

@RestController
@RequestMapping(PlayerSnapshotController.BASE_PLAYERS_SNAPSHOTS)
public class PlayerSnapshotController {

	public static final String BASE_PLAYERS_SNAPSHOTS = "/players/snapshots";
	public static final String CREATE = "/create";
	public static final String DELETE = "/delete";

	private final static Integer DEFAULT_PAGE_SIZE = 183;

	private final PlayerSnapshotService playerSnapshotService;

	private final ModelMapper modelMapper;

	public PlayerSnapshotController(final PlayerSnapshotService playerSnapshotService, final ModelMapper modelMapper) {
		super();
		this.playerSnapshotService = playerSnapshotService;
		this.modelMapper = modelMapper;
	}

	@GetMapping(params = { "id", "start", "end" })
	public List<PlayerSnapshotDto> byPlayerIdAndCreatedBetween(@RequestParam final Long id,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam final LocalDate start,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam final LocalDate end) {
		PageRequest page = PageRequest.of(0, DEFAULT_PAGE_SIZE);
		return playerSnapshotService.findByPlayerIdAndCreatedBetween(id, start, end, page) //
				.stream() //
				.map(this::snapshotToDto) //
				.collect(Collectors.toList());
	}

	@GetMapping(value = "/{id}")
	public List<PlayerSnapshotDto> byPlayerId(@PathVariable final Long id) {
		PageRequest page = PageRequest.of(0, DEFAULT_PAGE_SIZE);
		return playerSnapshotService.findByPlayerId(id, page) //
				.stream() //
				.map(this::snapshotToDto) //
				.collect(Collectors.toList());
	}

	@GetMapping(params = "clubName")
	public List<PlayerSnapshotDto> byClubName(@RequestParam final String clubName) {
		return this.byClubNameAndDate(clubName, LocalDate.now().minusDays(1));
	}

	@GetMapping(params = { "clubName", "date" })
	public List<PlayerSnapshotDto> byClubNameAndDate(@RequestParam final String clubName,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam final LocalDate date) {
		return playerSnapshotService.findByClubNameAndCreated(clubName, date).stream() //
				.map(this::snapshotToDto) //
				.collect(Collectors.toList());
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = CREATE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void add(@RequestBody final PlayerSnapshotDto playerSnapshot) {
		playerSnapshotService.save(snapshotToEntity(playerSnapshot));
	}

	@DeleteMapping(value = DELETE, params = "id")
	public void deleteById(@RequestParam final Long id) {
		playerSnapshotService.deleteById(id);
	}

	private PlayerSnapshotDto snapshotToDto(final PlayerSnapshotEntity playerSnapshotEntity) {
		return modelMapper.map(playerSnapshotEntity, PlayerSnapshotDto.class);
	}

	private PlayerSnapshotEntity snapshotToEntity(final PlayerSnapshotDto playerSnapshotDto) {
		return modelMapper.map(playerSnapshotDto, PlayerSnapshotEntity.class);
	}

}
