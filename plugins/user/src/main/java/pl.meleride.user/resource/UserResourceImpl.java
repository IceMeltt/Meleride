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
        user.setReputation(resultSet.getInt("reputation"));

        if (!(resultSet.getString("diseases").equals("[]")
            || resultSet.getString("diseases") == null)) {
          for (Disease disease : Disease.getDiseaseFromString(resultSet.getString("diseases")
              .replace("[", "")
              .replace("]", "")
              .replace(" ", "")
              .split(","))) {
            this.plugin.getUserManager().addDisease(user, disease);
          }
        }
      }
    } catch (SQLException | StorageException ex) {
      ex.printStackTrace();
    }
  }

  public void save(User user) {
    String query = "INSERT INTO `funcuser_users` (uuid, name, reputation, diseases) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE `name` = ?,"
        + "`reputation` = ?, `diseases` = ?";

    SQLStorageConsumer sqlStorageConsumer = preparedStatement -> {
      try {
        preparedStatement.setBytes(1, UniqueIdHelper.getBytesFromUUID(user.getIdentifier()));
        preparedStatement.setString(2, user.getName().get());
        preparedStatement.setInt(3, user.getReputation());
        preparedStatement.setString(4, Arrays.toString(user.getDiseases().toArray()));
        preparedStatement.setString(5, user.getName().get());
        preparedStatement.setInt(6, user.getReputation());
        preparedStatement.setString(7, Arrays.toString(user.getDiseases().toArray()));
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
    String query = "CREATE TABLE IF NOT EXISTS `funcuser_users` (`uuid` BINARY(16) PRIMARY KEY, `name` VARCHAR(16) NOT NULL UNIQUE, `reputation` SMALLINT NOT NULL, `diseases` TEXT NOT NULL)";

    try {
      this.plugin.getStorage().update(query);
    } catch (StorageException e) {
      e.printStackTrace();
    }
  }

}
