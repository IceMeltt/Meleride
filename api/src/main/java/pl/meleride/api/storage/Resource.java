package pl.meleride.api.storage;

import pl.meleride.api.entity.IdentifiableEntity;

public interface Resource<T extends IdentifiableEntity<?>> {

  void load();

  void save(T entity);

  void checkTable();

}
