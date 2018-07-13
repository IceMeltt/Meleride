package pl.meleride.economy.user;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import pl.meleride.api.flexible.BaseManager;
import pl.meleride.api.storage.StorageException;
import pl.meleride.api.storage.dao.IdentifiableEntityDao;
import pl.meleride.economy.MelerideEconomy;
import pl.meleride.economy.currency.Currency;

@SuppressWarnings("Duplicates")
public class EconomyUserDao implements IdentifiableEntityDao<EconomyUser> {

  private final MelerideEconomy instance;

  public EconomyUserDao(final MelerideEconomy instance) {
    this.instance = instance;
  }

  @Override
  public Set<EconomyUser> getAll() throws SQLException, StorageException {
    String query = "SELECT * FROM economy ORDER BY uuid";
    ResultSet result = this.instance.getStorage().query(query);
    Set<EconomyUser> list = new HashSet<>();

    while (result.next()) {
      EconomyUser user = new EconomyUserImpl(result.getString("uuid"));
      user.setName(result.getString("name"));

      user.setDataErrorStatus(result.getByte("dataError"));

      list.add(user);
    }
    return list;
  }

  @Override
  public void download(EconomyUser userToInject) throws SQLException, StorageException {
    String query = "SELECT * FROM economy WHERE uuid='" + userToInject.getUniqueId() + "';";
    ResultSet result = this.instance.getStorage().query(query);

    if (result.next()) {
      userToInject.setName(result.getString("name"));
      userToInject.setDataErrorStatus(result.getByte("dataError"));
    }
  }

  @Override
  public void downloadVariable(EconomyUser user, String variableInUser, String variableToDownload) throws SQLException, StorageException, NoSuchFieldException, IllegalAccessException {
    String query = "SELECT * FROM economy where uuid='" + user.getUniqueId() + "';";
    ResultSet result = this.instance.getStorage().query(query);

    if(result.next()) {
      Class<EconomyUser> gettedUser = EconomyUser.class;
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
  public void update(EconomyUser userToGet) throws StorageException  {
    StringBuilder sb = new StringBuilder("INSERT INTO economy (uuid, name, pln, eur, usd, czk, jpy, dataError) VALUES (")
        .append("'" + userToGet.getUniqueId().toString() + "',")
        .append("'" + userToGet.getName() + "',")
        .append("'" + userToGet.getCurrencyBalance(Currency.PLN) + "',")
        .append("'" + userToGet.getCurrencyBalance(Currency.EUR) + "',")
        .append("'" + userToGet.getCurrencyBalance(Currency.USD) + "',")
        .append("'" + userToGet.getCurrencyBalance(Currency.CZK) + "',")
        .append("'" + userToGet.getCurrencyBalance(Currency.JPY) + "',")
        .append("'" + userToGet.getDataErrorStatus() + "'")
        .append(") ON DUPLICATE KEY UPDATE ")
        .append("name='" + userToGet.getName() + "',")
        .append("pln='" + userToGet.getCurrencyBalance(Currency.PLN) + "',")
        .append("eur='" + userToGet.getCurrencyBalance(Currency.EUR) + "',")
        .append("usd='" + userToGet.getCurrencyBalance(Currency.USD) + "',")
        .append("czk='" + userToGet.getCurrencyBalance(Currency.CZK) + "',")
        .append("jpy='" + userToGet.getCurrencyBalance(Currency.JPY) + "',")
        .append("dataError='" + userToGet.getDataErrorStatus() + "';");

    this.instance.getStorage().update(sb.toString());
  }

  @Override
  public void delete(EconomyUser userToRemove) throws StorageException {
    String query = "DELETE FROM economy WHERE uuid='" + userToRemove.getUniqueId() + "';";
    this.instance.getStorage().update(query);
  }

  @Override
  public EconomyUser getFrom(String from, String value) throws SQLException, StorageException {
    String query = "SELECT * FROM economy WHERE " + from + "=" + value + ";";
    ResultSet result = this.instance.getStorage().query(query);

    if (result.next()) {
      BaseManager<EconomyUser> manager = this.instance.getManager();
      EconomyUser user;
      if (manager.getUser(value).isPresent()) {
        user = manager.getUser(value).get();
        return user;
      }
    }
    return null;
  }

  @Override
  public EconomyUser getFrom(String uuid) throws SQLException, StorageException {
    String query = "SELECT * FROM economy WHERE uuid=" + uuid + ";";
    ResultSet result = this.instance.getStorage().query(query);

    if (result.next()) {
      BaseManager<EconomyUser> manager = this.instance.getManager();
      EconomyUser user;
      if (manager.getUser(uuid).isPresent()) {
        user = manager.getUser(uuid).get();
        return user;
      }
    }
    return null;
  }

}
