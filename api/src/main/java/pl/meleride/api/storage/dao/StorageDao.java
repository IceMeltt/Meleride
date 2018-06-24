package pl.meleride.api.storage.dao;

import java.util.List;

public interface StorageDao<T> {

  List<T> getAll(String table, String label);
  Object getVariable(String table, String key, Object value, String whatYouAreLookingFor);

}
