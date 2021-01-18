package pkabus.comuniostatsbackend.persistence.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class PlayerEntity {

	@Id
	@GeneratedValue
	private Long id;

	private String comunioId;

	private String name;

//	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//	@JoinColumn(name = "player_id")
//	private Set<PlayerSnapshotEntity> playerSnapshots;

	public PlayerEntity() {
		//
	}

	public PlayerEntity(final String comunioId, final String name) {
		super();
		this.comunioId = comunioId;
		this.name = name;
	}

	public PlayerEntity(final PlayerEntity player) {
		this(player.comunioId, player.name);
	}

	public String getName() {
		return name;
	}

	public Long getId() {
		return id;
	}

	public String getComunioId() {
		return comunioId;
	}

//	public Set<PlayerSnapshotEntity> getPlayerSnapshots() {
//		return playerSnapshots;
//	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comunioId == null) ? 0 : comunioId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
//		result = prime * result + ((playerSnapshots == null) ? 0 : playerSnapshots.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayerEntity other = (PlayerEntity) obj;
		if (comunioId == null) {
			if (other.comunioId != null)
				return false;
		} else if (!comunioId.equals(other.comunioId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
//		if (playerSnapshots == null) {
//			if (other.playerSnapshots != null)
//				return false;
//		} else if (!playerSnapshots.equals(other.playerSnapshots))
//			return false;
		return true;
	}
}
