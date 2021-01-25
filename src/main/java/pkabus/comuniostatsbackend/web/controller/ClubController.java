package pkabus.comuniostatsbackend.web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import pkabus.comuniostatsbackend.persistence.model.ClubEntity;
import pkabus.comuniostatsbackend.service.ClubService;
import pkabus.comuniostatsbackend.web.dto.ClubDto;

@RestController
@RequestMapping("/clubs")
public class ClubController {

	private ClubService clubService;

	private ModelMapper modelMapper;

	public ClubController(final ClubService clubService, final ModelMapper modelMapper) {
		super();
		this.clubService = clubService;
		this.modelMapper = modelMapper;
	}

	@GetMapping(value = "/{id}")
	public ClubDto byId(@PathVariable Long id) {
		ClubEntity clubEntity = clubService.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return toDto(clubEntity);
	}

	@PostMapping
	public void create(@RequestBody ClubDto club) {
		clubService.save(toEntity(club));
	}

	private ClubDto toDto(ClubEntity clubEntity) {
		return modelMapper.map(clubEntity, ClubDto.class);
	}

	private ClubEntity toEntity(ClubDto clubDto) {
		return modelMapper.map(clubDto, ClubEntity.class);
	}
}
