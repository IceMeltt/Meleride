package pl.meleride.api.storage.dao;

import java.util.Optional;
import pl.meleride.api.entity.IdentifiableEntity;

public interface IdentifiableEntityDao<T extends IdentifiableEntity<?>, ID> {

  Optional<T> findById(ID id);

  Iterable<T> findAll();

  Iterable<T> findAllById(Iterable<ID> ids);

  void delete(T t);

  void deleteById(ID id);

  void deleteAll();

  void save(T t);

}
