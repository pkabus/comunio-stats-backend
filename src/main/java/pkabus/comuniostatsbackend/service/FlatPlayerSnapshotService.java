package pkabus.comuniostatsbackend.service;

import java.util.List;
import java.util.stream.Stream;

import pkabus.comuniostatsbackend.persistence.model.PlayerSnapshotEntity;

public interface FlatPlayerSnapshotService {

	PlayerSnapshotEntity save(PlayerSnapshotEntity playerSnapshot);

	List<PlayerSnapshotEntity> saveAll(Stream<PlayerSnapshotEntity> map);

}
