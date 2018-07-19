package pl.meleride.user.resource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;
import pl.meleride.api.helper.UniqueIdHelper;
import pl.meleride.api.storage.Resource;
import pl.meleride.api.storage.StorageException;
import pl.meleride.api.storage.sql.SQLStorageConsumer;
import pl.meleride.user.MelerideUser;
import pl.meleride.user.disease.Disease;
import pl.meleride.user.entity.User;
import pl.meleride.user.entity.impl.UserImpl;

public class UserResourceImpl implements Resource<User> {

  private final MelerideUser plugin;

  public UserResourceImpl(MelerideUser plugin) {
    this.plugin = plugin;
  }

  public void load() {
    String query = "SELECT * FROM `funcuser_users`";

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
        for (Disease disease : Disease.getDiseaseFromString(resultSet.getString("diseases")
            .replace("[", "")
            .replace("]", "")
            .replace(" ", "")
            .split(","))) {
          user.addDisease(disease);
        }
      }
    } catch (SQLException | StorageException ex) {
      ex.printStackTrace();
    }
  }

  public void save(User user) {
    String query = "INSERT INTO `funcuser_users` (uuid, name, diseases) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE `name` = ?";

    SQLStorageConsumer sqlStorageConsumer = preparedStatement -> {
      try {
        preparedStatement.setBytes(1, UniqueIdHelper.getBytesFromUUID(user.getIdentifier()));
        preparedStatement.setString(2, user.getName().get());
        preparedStatement.setString(3, Arrays.toString(user.getDiseases().toArray()));
        preparedStatement.setString(4, user.getName().get());
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
    String query = "CREATE TABLE IF NOT EXISTS `funcuser_users` (`uuid` BINARY(16) PRIMARY KEY, `name` VARCHAR(16) NOT NULL UNIQUE, `diseases` TEXT NOT NULL)";

    try {
      this.plugin.getStorage().update(query);
    } catch (StorageException e) {
      e.printStackTrace();
    }
  }

}
