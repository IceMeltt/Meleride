package pl.meleride.economy.resource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import pl.meleride.api.helper.UniqueIdHelper;
import pl.meleride.api.storage.Resource;
import pl.meleride.api.storage.StorageException;
import pl.meleride.api.storage.sql.SQLStorageConsumer;
import pl.meleride.economy.MelerideEconomy;
import pl.meleride.economy.currency.Currency;
import pl.meleride.economy.entity.User;
import pl.meleride.economy.entity.impl.UserImpl;

public class UserResourceImpl implements Resource<User> {

  private final MelerideEconomy plugin;

  public UserResourceImpl(MelerideEconomy plugin) {
    this.plugin = plugin;
  }

  public void load(User user) {
    String query = "SELECT * FROM `economy_users` WHERE `uuid` = UNHEX('" + user.getIdentifier().toString().replace("-", "") + "');";

    try {
      ResultSet resultSet = this.plugin.getStorage().query(query);

      while (resultSet.next()) {
        UUID uniqueId = UniqueIdHelper.getUUIDFromBytes(resultSet.getBytes("uuid"));

        user = this.plugin.getUserManager().getUser(uniqueId).orElseGet(() -> {
          User newUser = new UserImpl(uniqueId);
          this.plugin.getUserManager().addUser(newUser);
          return newUser;
        });

        user.setName(resultSet.getString("name"));
        this.plugin.getUserManager().add(user, Currency.PLN, resultSet.getDouble("handledpln"));
        this.plugin.getUserManager().add(user, Currency.EUR, resultSet.getDouble("handledeur"));
        this.plugin.getUserManager().add(user, Currency.CZK, resultSet.getDouble("handledczk"));
        this.plugin.getUserManager().add(user, Currency.USD, resultSet.getDouble("handledusd"));
        this.plugin.getUserManager().add(user, Currency.JPY, resultSet.getDouble("handledjpy"));
      }
    } catch (SQLException | StorageException ex) {
      ex.printStackTrace();
    }
  }

  public void save(User user) {
    String query = "INSERT INTO `economy_users` (uuid, name, handledpln, handledeur, handledczk, handledusd, handledjpy) VALUES (?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE `name` = ?, "
        + "handledpln = ?, handledeur = ?, handledczk = ?, handledusd = ?, handledjpy = ?"; //13?

    SQLStorageConsumer sqlStorageConsumer = preparedStatement -> {
      try {
        preparedStatement.setBytes(1, UniqueIdHelper.getBytesFromUUID(user.getIdentifier()));
        preparedStatement.setString(2, user.getName().get());
        preparedStatement.setDouble(3, user.getCurrencyBalance(Currency.PLN));
        preparedStatement.setDouble(4, user.getCurrencyBalance(Currency.EUR));
        preparedStatement.setDouble(5, user.getCurrencyBalance(Currency.CZK));
        preparedStatement.setDouble(6, user.getCurrencyBalance(Currency.USD));
        preparedStatement.setDouble(7, user.getCurrencyBalance(Currency.JPY));
        preparedStatement.setString(8, user.getName().get());
        preparedStatement.setDouble(9, user.getCurrencyBalance(Currency.PLN));
        preparedStatement.setDouble(10, user.getCurrencyBalance(Currency.EUR));
        preparedStatement.setDouble(11, user.getCurrencyBalance(Currency.CZK));
        preparedStatement.setDouble(12, user.getCurrencyBalance(Currency.USD));
        preparedStatement.setDouble(13, user.getCurrencyBalance(Currency.JPY));
      } catch (SQLException e) {
        e.printStackTrace();
      }
    };

    try {
      this.plugin.getStorage().update(query, sqlStorageConsumer);
    } catch (StorageException e) {
      e.printStackTrace();
    }
  }

  public void checkTable() {
    String query = "CREATE TABLE IF NOT EXISTS `economy_users` (`uuid` BINARY(16) PRIMARY KEY, `name` VARCHAR(16) NOT NULL UNIQUE, `handledpln` DOUBLE NOT NULL, "
        + "`handledeur` DOUBLE NOT NULL, `handledczk` DOUBLE NOT NULL, `handledusd` DOUBLE NOT NULL, `handledjpy` DOUBLE NOT NULL)";

    try {
      this.plugin.getStorage().update(query);
    } catch (StorageException e) {
      e.printStackTrace();
    }
  }

}
