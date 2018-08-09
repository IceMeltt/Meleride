package pl.meleride.base.resource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import pl.meleride.api.helper.UniqueIdHelper;
import pl.meleride.api.storage.Resource;
import pl.meleride.api.storage.StorageException;
import pl.meleride.api.storage.sql.SQLStorageConsumer;
import pl.meleride.base.MelerideBase;
import pl.meleride.base.entity.User;
import pl.meleride.base.entity.impl.UserImpl;

public class UserResourceImpl implements Resource<User> {

  private final MelerideBase plugin;

  public UserResourceImpl(MelerideBase plugin) {
    this.plugin = plugin;
  }

  public void load(User user) {
    String query = "SELECT * FROM `base_users` WHERE `uuid` = UNHEX('" + user.getIdentifier().toString().replace("-", "") + "');";

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
        user.setDrugCooldown(resultSet.getLong("drugcooldown"));
      }
    } catch (SQLException | StorageException ex) {
      ex.printStackTrace();
    }
  }

  public void save(User user) {
    String query = "INSERT INTO `base_users` (uuid, name, drugcooldown) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE `name` = ?, "
        + "`drugcooldown` = ?";

    SQLStorageConsumer sqlStorageConsumer = preparedStatement -> {
      try {
        preparedStatement.setBytes(1, UniqueIdHelper.getBytesFromUUID(user.getIdentifier()));
        preparedStatement.setString(2, user.getName().get());
        preparedStatement.setLong(3, user.getDrugCooldown());
        preparedStatement.setString(4, user.getName().get());
        preparedStatement.setLong(5, user.getDrugCooldown());
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
    String query = "CREATE TABLE IF NOT EXISTS `base_users` (`uuid` BINARY(16) PRIMARY KEY, `name` VARCHAR(16) NOT NULL UNIQUE, `drugcooldown` BIGINT NOT NULL)";

    try {
      this.plugin.getStorage().update(query);
    } catch (StorageException e) {
      e.printStackTrace();
    }
  }

}
