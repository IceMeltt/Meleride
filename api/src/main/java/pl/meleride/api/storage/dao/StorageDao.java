package pl.meleride.api.storage.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import pl.meleride.api.storage.StorageException;

public interface StorageDao<T> {

  Set<T> getAll() throws SQLException, StorageException;

  void download(T objectToInject) throws SQLException, StorageException;

  void downloadVariable(T objectToDownload, String variableInT, String variableToDownload) throws SQLException, StorageException, NoSuchFieldException, IllegalAccessException;

  void updateDatabaseVariable(String key, Object value) throws StorageException;

  void update(T objectToGet) throws StorageException;

  void delete(T objectToRemove) throws StorageException;

  T getFrom(String from, String value) throws SQLException, StorageException;

  T getFrom(String uuid) throws SQLException, StorageException;

}
