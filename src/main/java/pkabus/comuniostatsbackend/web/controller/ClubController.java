package pkabus.comuniostatsbackend.web.controller;

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

	public ClubController(ClubService clubService) {
		super();
		this.clubService = clubService;
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

	private static ClubDto toDto(ClubEntity clubEntity) {
		return new ClubDto(clubEntity.getId(), clubEntity.getName());
	}

	private static ClubEntity toEntity(ClubDto clubDto) {
		return new ClubEntity(clubDto.getId(), clubDto.getName());
	}
}
