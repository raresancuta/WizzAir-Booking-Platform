package ubb.scs.map.zboruri_practic.Repository;

import ubb.scs.map.zboruri_practic.Domain.Entity;

import java.util.List;
import java.util.Optional;

public interface Repository<ID,E extends Entity<ID>> {
    public Optional<E> find(ID id);
}
