package pl.meleride.api.storage.dao;

import java.sql.SQLException;
import java.util.List;
import pl.meleride.api.storage.StorageException;
import pl.meleride.api.user.User;

public interface StorageDao<T> {

  List<User> getAll() throws SQLException, StorageException;

  void download(T objectToInject) throws SQLException, StorageException;

  void update(T objectToGet) throws StorageException;

  void delete(T objectToRemove) throws StorageException;

  User getFrom(String from, String value) throws SQLException, StorageException;

}
