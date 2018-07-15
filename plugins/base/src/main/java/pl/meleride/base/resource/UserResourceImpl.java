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

  public void load() {
    String query = "SELECT * FROM `users`";

    try {
      ResultSet resultSet = this.plugin.getStorage().query(query);

      while (resultSet.next()) {
        UUID uniqueId = UniqueIdHelper.getUUIDFromBytes(resultSet.getBytes("uuid"));

        User user = this.plugin.getUserManager().getUser(uniqueId).orElseGet(() -> {
          User newUser = new UserImpl(uniqueId);
          this.plugin.getUserManager().addUser(newUser);
          return newUser;
        });

        user.setName(resultSet.getString("name"));
      }
    } catch (SQLException | StorageException ex) {
      ex.printStackTrace();
    }
  }

  public void save(User user) {
    String query = "INSERT INTO `users` (uuid, name) VALUES (?, ?) ON DUPLICATE KEY UPDATE `name` = ?";

    SQLStorageConsumer sqlStorageConsumer = preparedStatement -> {
      try {
        preparedStatement.setBytes(1, UniqueIdHelper.getBytesFromUUID(user.getIdentifier()));
        preparedStatement.setString(2, user.getName().get());
        preparedStatement.setString(3, user.getName().get());
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
    String query = "CREATE TABLE IF NOT EXISTS `users` (`uuid` BINARY(16) PRIMARY KEY, `name` VARCHAR(16) NOT NULL UNIQUE)";

    try {
      this.plugin.getStorage().update(query);
    } catch (StorageException e) {
      e.printStackTrace();
    }
  }

}
