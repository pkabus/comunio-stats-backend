package pkabus.comuniostatsbackend.web.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import pkabus.comuniostatsbackend.persistence.model.ClubEntity;
import pkabus.comuniostatsbackend.service.ClubService;
import pkabus.comuniostatsbackend.web.dto.ClubDto;

@RestController
@RequestMapping("/clubs")
public class ClubController {

	private final ClubService clubService;

	private final ModelMapper modelMapper;

	public ClubController(final ClubService clubService, final ModelMapper modelMapper) {
		super();
		this.clubService = clubService;
		this.modelMapper = modelMapper;
	}

	@GetMapping(value = "/all")
    @CrossOrigin
	public List<ClubDto> all() {
		return StreamSupport.stream(clubService.findAll().spliterator(), false) //
				.map(this::toDto) //
				.collect(Collectors.toList());
	}

	@GetMapping(params = "id")
	public ClubDto byId(@RequestParam final Long id) {
		ClubEntity clubEntity = clubService.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return toDto(clubEntity);
	}

	@GetMapping(params = "name")
	public ClubDto byName(@RequestParam final String name) {
		ClubEntity clubEntity = clubService.findByName(name)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return toDto(clubEntity);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@RequestBody final ClubDto club) {
		clubService.save(toEntity(club));
	}

	@DeleteMapping(value = "/delete", params = "byName")
	public void deleteByName(@RequestParam final String byName) {
		clubService.deleteByName(byName);
	}

	public void delete(final ClubDto club) {
		clubService.delete(toEntity(club));
	}

	public void deleteAll() {
		clubService.deleteAll();
	}

	private ClubDto toDto(final ClubEntity clubEntity) {
		return modelMapper.map(clubEntity, ClubDto.class);
	}

	private ClubEntity toEntity(final ClubDto clubDto) {
		return modelMapper.map(clubDto, ClubEntity.class);
	}

}
