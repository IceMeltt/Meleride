package pl.meleride.api.storage.resource;

import pl.meleride.api.storage.Basic;

public interface Resource<T extends Basic> {

  void load();

  void save(T t);

  void checkTable();

}
