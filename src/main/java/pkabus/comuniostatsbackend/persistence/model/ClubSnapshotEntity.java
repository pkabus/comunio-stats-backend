package pkabus.comuniostatsbackend.persistence.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ClubSnapshotEntity {

	@Id
	@GeneratedValue
	private Long id;

	private LocalDate created;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "club_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private ClubEntity club;

	public ClubSnapshotEntity() {
		super();
	}

	public ClubSnapshotEntity(final Long id, final ClubEntity club) {
		super();
		this.id = id;
		this.created = LocalDate.now();
		this.club = club;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((club == null) ? 0 : club.hashCode());
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ClubSnapshotEntity other = (ClubSnapshotEntity) obj;
		if (club == null) {
			if (other.club != null) {
				return false;
			}
		} else if (!club.equals(other.club)) {
			return false;
		}
		if (created == null) {
			if (other.created != null) {
				return false;
			}
		} else if (!created.equals(other.created)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ClubSnapshotEntity [id=" + id + ", created=" + created + ", club=" + club + "]";
	}
}
