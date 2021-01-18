package pkabus.comuniostatsbackend.persistence.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ClubEntity {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

//	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//	@JoinColumn(name = "club_id")
//	private Set<PlayerEntity> players;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
