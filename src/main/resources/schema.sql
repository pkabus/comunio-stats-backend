CREATE TABLE club_entity (
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(128),
	PRIMARY KEY (id)
);

CREATE TABLE player_entity (
	id BIGINT NOT NULL AUTO_INCREMENT,
	link VARCHAR(128),
	name VARCHAR(128),
	PRIMARY KEY (id)
);

CREATE TABLE player_snapshot_entity (
	id BIGINT NOT NULL AUTO_INCREMENT,
	player_id BIGINT,
	club_id BIGINT,
	position VARCHAR(128),
	points_during_current_season INT,
	market_value BIGINT,
	created DATE,
	PRIMARY KEY (id),
	FOREIGN KEY (player_id) REFERENCES player_entity(id),
	FOREIGN KEY (club_id) REFERENCES club_entity(id)
);

CREATE TABLE hibernate_sequence (
	next_val BIGINT
);
