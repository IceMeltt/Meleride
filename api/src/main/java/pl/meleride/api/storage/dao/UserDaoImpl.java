package pl.meleride.api.storage.dao;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import pl.meleride.api.MelerideAPI;
import pl.meleride.api.economy.currency.Currency;
import pl.meleride.api.storage.StorageException;
import pl.meleride.api.user.User;
import pl.meleride.api.user.UserImpl;
import pl.meleride.api.user.manager.UserManager;
import pl.meleride.api.user.manager.UserManagerImpl;
import pl.meleride.api.user.status.DiseaseStatus;

@SuppressWarnings("Duplicates")
public class UserDaoImpl implements StorageDao<User> {

  private final MelerideAPI instance;

  public UserDaoImpl(final MelerideAPI instance) {
    this.instance = instance;
  }

  @Override
  public Set<User> getAll() throws SQLException, StorageException {
    String query = "SELECT * FROM users ORDER BY uuid";
    ResultSet result = this.instance.getStorage().query(query);
    Set<User> list = new HashSet<>();

    while (result.next()) {
      User user = new UserImpl(result.getString("uuid"));
      user.setName(result.getString("name"));
      user.add(Currency.PLN, result.getDouble("money"));
      user.setDataErrorStatus(result.getByte("dataError"));

      for (DiseaseStatus disease : DiseaseStatus.getDiseaseFromString(result.getString("disease").split(","))) {
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

    if (result.next()) {
      userToInject.setName(result.getString("name"));
      userToInject.add(Currency.PLN, result.getDouble("money"));
      userToInject.setDataErrorStatus(result.getByte("dataError"));

      if (!(result.getString("disease").equals("[]")
          || result.getString("disease") == null)) {
        for (DiseaseStatus disease : DiseaseStatus.getDiseaseFromString(result.getString("disease")
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
  public void downloadVariable(User user, String variableInUser, String variableToDownload) throws SQLException, StorageException, NoSuchFieldException, IllegalAccessException {
    String query = "SELECT * FROM users where uuid='" + user.getUniqueId() + "';";
    ResultSet result = this.instance.getStorage().query(query);

    if(result.next()) {
      Class<User> gettedUser = User.class;
      if(gettedUser.getDeclaredField(variableInUser).isAccessible()) {
        Field field = gettedUser.getField(variableInUser);
        field.set(this, result.getObject(variableToDownload));
      }
    }
  }

  @Override
  public void updateDatabaseVariable(String key, Object value) throws StorageException {
    String query = "UPDATE " + key + "='" + value + "';";
    this.instance.getStorage().update(query);
  }

  @Override
  public void update(User userToGet) throws StorageException  {
    StringBuilder sb = new StringBuilder("INSERT INTO users (uuid, name, disease, money,dataError) VALUES (")
        .append("'" + userToGet.getUniqueId().toString() + "',")
        .append("'" + userToGet.getName() + "',")
        .append("'" + Arrays.toString(userToGet.getDiseases().toArray()) + "',")
        .append("'" + userToGet.getCurrencyBalance(Currency.PLN) + "',")
        .append("'" + userToGet.getDataErrorStatus() + "'")
        .append(") ON DUPLICATE KEY UPDATE ")
        .append("name='" + userToGet.getName() + "',")
        .append("disease='" + userToGet.getDiseases() + "',")
        .append("money='" + userToGet.getCurrencyBalance(Currency.PLN) + "',")
        .append("dataError='" + userToGet.getDataErrorStatus() + "';");

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

    if (result.next()) {
      UserManager manager = new UserManagerImpl();
      User user;
      if (manager.getUser(value).isPresent()) {
        user = manager.getUser(value).get();
        return user;
      }
    }
    return null;
  }

  @Override
  public User getFrom(String uuid) throws SQLException, StorageException {
    String query = "SELECT * FROM users WHERE uuid=" + uuid + ";";
    ResultSet result = this.instance.getStorage().query(query);

    if (result.next()) {
      UserManager manager = new UserManagerImpl();
      User user;
      if (manager.getUser(uuid).isPresent()) {
        user = manager.getUser(uuid).get();
        return user;
      }
    }
    return null;
  }

}
