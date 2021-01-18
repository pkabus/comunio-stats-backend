package pkabus.comuniostatsbackend.web.dto;

import java.time.LocalDate;

public class ClubSnapshotDto {

	private Long id;

	private LocalDate dateCreated;

	private ClubDto club;

	public ClubSnapshotDto() {
		super();
	}

	public ClubSnapshotDto(Long id, LocalDate dateCreated, ClubDto club) {
		super();
		this.id = id;
		this.dateCreated = dateCreated;
		this.club = club;
	}

	public Long getId() {
		return id;
	}

	public LocalDate getDateCreated() {
		return dateCreated;
	}

	public ClubDto getClub() {
		return club;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((club == null) ? 0 : club.hashCode());
		result = prime * result + ((dateCreated == null) ? 0 : dateCreated.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ClubSnapshotDto other = (ClubSnapshotDto) obj;
		if (club == null) {
			if (other.club != null)
				return false;
		} else if (!club.equals(other.club))
			return false;
		if (dateCreated == null) {
			if (other.dateCreated != null)
				return false;
		} else if (!dateCreated.equals(other.dateCreated))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ClubSnapshotDto [id=" + id + ", dateCreated=" + dateCreated + ", club=" + club + "]";
	}
}
