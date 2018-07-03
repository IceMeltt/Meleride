package pl.meleride.api.storage.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.entity.Player;
import pl.meleride.api.MelerideAPI;
import pl.meleride.api.storage.StorageException;
import pl.meleride.api.user.User;
import pl.meleride.api.user.UserImpl;
import pl.meleride.api.user.manager.UserManager;
import pl.meleride.api.user.manager.UserManagerImpl;
import pl.meleride.api.user.status.DiseaseStatus;

public class UserDaoImpl implements StorageDao<User> {

  private final MelerideAPI instance;

  public UserDaoImpl(final MelerideAPI instance) {
    this.instance = instance;
  }

  @Override
  public List<User> getAll() throws SQLException, StorageException {
    ResultSet result = this.instance.getStorage().query("SELECT * FROM users ORDER BY uuid");
    List<User> list = new ArrayList<>();

    while(result.next()) {
      User user = new UserImpl(result.getString("uuid"));
      user.setName(result.getString("name"));
      for(DiseaseStatus disease : DiseaseStatus.getDiseaseFromString(result.getString("disease").split(","))) {
        user.addDisease(disease);
      }
      list.add(user);
    }
    return list;
  }

  @Override
  public void download(User userToInject) throws SQLException, StorageException {
    String query = "SELECT * FROM users WHERE uuid='" + userToInject.getUniqueId() + "';";
    ResultSet result = this.instance.getStorage().query(query);
    if(result.next()) {
      userToInject.setName(result.getString("name"));

      if(!(result.getString("disease").equals("[]")
          || result.getString("disease") == null)) {
        for(DiseaseStatus disease : DiseaseStatus.getDiseaseFromString(result.getString("disease")
            .replace("[", "")
            .replace("]", "")
            .replace(" ", "")
            .split(","))) {
          userToInject.addDisease(disease);
        }
      }
    }
  }

  @Override
  public void update(User userToGet) throws StorageException  {
    StringBuilder sb = new StringBuilder("INSERT INTO users (uuid, name, disease) VALUES (")
        .append("'" + userToGet.getUniqueId().toString() + "',")
        .append("'" + userToGet.getName() + "',")
        .append("'" + Arrays.toString(userToGet.getDiseases().toArray()) + "'")
        .append(") ON DUPLICATE KEY UPDATE ")
        .append("name='" + userToGet.getName() + "',")
        .append("disease='" + userToGet.getDiseases() + "';");

    this.instance.getStorage().update(sb.toString());
  }

  @Override
  public void delete(User userToRemove) throws StorageException {
    String query = "DELETE FROM users WHERE uuid='" + userToRemove.getUniqueId() + "';";
    this.instance.getStorage().update(query);
  }

  @Override
  public User getFrom(String from, String value) throws SQLException, StorageException {
    String query = "SELECT * FROM users WHERE " + from + "=" + value + ";";
    ResultSet result = this.instance.getStorage().query(query);

    if(result.next()) {
      UserManager manager = new UserManagerImpl();
      User user;
      if(manager.getUser(value).isPresent()) {
        user = manager.getUser(value).get();
        return user;
      }
    }
    return null;
  }

}
