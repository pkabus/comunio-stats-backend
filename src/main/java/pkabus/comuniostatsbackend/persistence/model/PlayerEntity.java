package pkabus.comuniostatsbackend.persistence.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class PlayerEntity {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	private String comunioId;

	public PlayerEntity() {
		//
	}

	public PlayerEntity(final String name, final String comunioId) {
		super();
		this.name = name;
		this.comunioId = comunioId;
	}

	public PlayerEntity(final PlayerEntity playerEntity) {
		this(playerEntity.name, playerEntity.comunioId);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getComunioId() {
		return comunioId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comunioId == null) ? 0 : comunioId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		return true;
	}

	@Override
	public String toString() {
		return "PlayerEntity [id=" + id + ", name=" + name + ", comunioId=" + comunioId + "]";
	}

}
