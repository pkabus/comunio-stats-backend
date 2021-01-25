package pkabus.comuniostatsbackend.web.dto;

import java.time.LocalDate;

public class PlayerSnapshotDto {

	private Long id;

	private PlayerDto player;

	private Long marketValue;

	private Integer points;

	private LocalDate dateCreated;

	private String position;

	public PlayerSnapshotDto() {
		super();
	}

	public PlayerSnapshotDto(PlayerDto player, Long marketValue, Integer points, LocalDate dateCreated,
			String position) {
		super();
		this.player = player;
		this.marketValue = marketValue;
		this.points = points;
		this.dateCreated = dateCreated;
		this.position = position;
	}

	public Long getId() {
		return id;
	}

	public PlayerDto getPlayer() {
		return player;
	}

	public Long getMarketValue() {
		return marketValue;
	}

	public Integer getPoints() {
		return points;
	}

	public LocalDate getDateCreated() {
		return dateCreated;
	}

	public String getPosition() {
		return position;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateCreated == null) ? 0 : dateCreated.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((marketValue == null) ? 0 : marketValue.hashCode());
		result = prime * result + ((points == null) ? 0 : points.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
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
		PlayerSnapshotDto other = (PlayerSnapshotDto) obj;
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
		if (marketValue == null) {
			if (other.marketValue != null)
				return false;
		} else if (!marketValue.equals(other.marketValue))
			return false;
		if (points == null) {
			if (other.points != null)
				return false;
		} else if (!points.equals(other.points))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PlayerSnapshotDto [id=" + id + ", player=" + player + ", marketValue=" + marketValue + ", points="
				+ points + ", dateCreated=" + dateCreated + ", position=" + position + "]";
	}

}
