package pl.meleride.api.storage.dao;

import java.sql.SQLException;
import java.util.List;
import pl.meleride.api.storage.StorageException;

public interface StorageDao<T> {

  List<T> getAll() throws SQLException, StorageException;

  void download(T objectToInject) throws SQLException, StorageException;

  void update(T objectToGet) throws StorageException;

  void delete(T objectToRemove) throws StorageException;

  T getFrom(String from, String value) throws SQLException, StorageException;

}
