package pkabus.comuniostatsbackend.web.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pkabus.comuniostatsbackend.persistence.model.PlayerSnapshotEntity;
import pkabus.comuniostatsbackend.service.PlayerSnapshotService;
import pkabus.comuniostatsbackend.web.dto.PlayerSnapshotDto;

@RestController
@RequestMapping("/players/snapshots")
public class PlayerSnapshotController {

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
			@RequestParam final LocalDate start, @RequestParam final LocalDate end) {
		PageRequest page = PageRequest.of(0, DEFAULT_PAGE_SIZE);
		return playerSnapshotService.findByPlayerIdAndDateCreatedBetween(id, start, end, page) //
				.stream() //
				.map(this::snapshotToDto) //
				.collect(Collectors.toList());
	}

	@GetMapping(params = "id")
	public List<PlayerSnapshotDto> byPlayerId(@RequestParam final Long id) {
		PageRequest page = PageRequest.of(0, DEFAULT_PAGE_SIZE);
		return playerSnapshotService.findByPlayerId(id, page) //
				.stream() //
				.map(this::snapshotToDto) //
				.collect(Collectors.toList());
	}

	@PostMapping("/add")
	public void addSnapshot(@RequestBody final PlayerSnapshotDto playerSnapshot) {
		playerSnapshotService.save(snapshotToEntity(playerSnapshot));
	}

	private PlayerSnapshotDto snapshotToDto(final PlayerSnapshotEntity playerSnapshotEntity) {
		return modelMapper.map(playerSnapshotEntity, PlayerSnapshotDto.class);
	}

	private PlayerSnapshotEntity snapshotToEntity(final PlayerSnapshotDto playerSnapshotDto) {
		return modelMapper.map(playerSnapshotDto, PlayerSnapshotEntity.class);
	}

}
