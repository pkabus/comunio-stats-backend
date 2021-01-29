package pkabus.comuniostatsbackend.web.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import pkabus.comuniostatsbackend.persistence.model.ClubEntity;
import pkabus.comuniostatsbackend.persistence.model.PlayerEntity;
import pkabus.comuniostatsbackend.persistence.model.PlayerSnapshotEntity;
import pkabus.comuniostatsbackend.service.PlayerSnapshotService;
import pkabus.comuniostatsbackend.web.dto.FlatPlayerSnapshotDto;

@RestController
@RequestMapping(FlatPlayerSnapshotController.BASE_FLAT_SNAPSHOTS)
public class FlatPlayerSnapshotController {

	public static final String BASE_FLAT_SNAPSHOTS = "/flatsnapshots";
	public static final String CREATE = "/create";

	private final PlayerSnapshotService playerSnapshotService;

	private final ModelMapper modelMapper;

	public FlatPlayerSnapshotController(final PlayerSnapshotService playerSnapshotService,
			final ModelMapper modelMapper) {
		super();
		this.playerSnapshotService = playerSnapshotService;
		this.modelMapper = modelMapper;
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = CREATE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void addAll(@RequestBody final List<FlatPlayerSnapshotDto> dtos) {
		playerSnapshotService.saveAll(dtos.stream().map(this::flatSnapshotToPlayerSnapshotEntity));
	}

	private PlayerSnapshotEntity flatSnapshotToPlayerSnapshotEntity(final FlatPlayerSnapshotDto flatPlayerSnapshotDto) {
		PlayerSnapshotEntity playerSnapshotEntity = modelMapper.map(flatPlayerSnapshotDto, PlayerSnapshotEntity.class);
		PlayerEntity playerEntity = modelMapper.map(flatPlayerSnapshotDto, PlayerEntity.class);
		playerSnapshotEntity.setPlayer(playerEntity);

		ClubEntity clubEntity = modelMapper.map(flatPlayerSnapshotDto, ClubEntity.class);
		playerSnapshotEntity.setClub(clubEntity);

		return playerSnapshotEntity;
	}
}