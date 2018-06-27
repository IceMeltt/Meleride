package pl.meleride.api.storage.dao;

import java.sql.SQLException;
import java.util.List;
import org.bukkit.entity.Player;
import pl.meleride.api.storage.StorageException;
import pl.meleride.api.user.User;

public interface UserDao {

  List<User> getAll() throws SQLException, StorageException;

  void make(Player player) throws StorageException;

  void download(User userToInject) throws SQLException, StorageException;

  void update(User userToGet) throws StorageException;

  void delete(User userToRemove) throws StorageException;

  User getFrom(String from, String value) throws SQLException, StorageException;

}
