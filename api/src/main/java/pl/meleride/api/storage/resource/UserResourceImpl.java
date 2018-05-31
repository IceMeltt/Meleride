package pl.meleride.api.storage.resource;

import pl.meleride.api.UniqueIdUtil;
import pl.meleride.api.storage.query.DatabaseQueryImpl;
import pl.meleride.api.storage.StatementFactory;
import pl.meleride.api.user.User;
import pl.meleride.api.user.UserImpl;
import pl.meleride.api.user.manager.UserManager;

import javax.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

public class UserResourceImpl implements Resource<User> {

  private final UserManager userManager;
  private final ExecutorService executorService;

  @Inject
  UserResourceImpl(UserManager userManager, ExecutorService executorService) {
    this.userManager = userManager;
    this.executorService = executorService;
  }

  @Override
  public void load() {
    PreparedStatement preparedStatement = StatementFactory.getStatement("players-select");
    ResultSet resultSet = new DatabaseQueryImpl(this.executorService, preparedStatement).executeQuery();

    Consumer<ResultSet> consumer = result -> {
      try {
        while (resultSet.next()) {
          UUID uniqueId = UniqueIdUtil.getUUIDFromBytes(resultSet.getBytes("uuid"));

          User user = this.userManager.getUser(uniqueId).orElseGet(() -> {
            User newUser = new UserImpl(uniqueId);
            this.userManager.addUser(newUser);
            return newUser;
          });

          user.setName(resultSet.getString("name"));

          preparedStatement.close();
        }
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    };

    consumer.accept(resultSet);
  }

  @Override
  public void save(User user) {
    try (PreparedStatement preparedStatement = StatementFactory.getStatement("players-insert")) {
      preparedStatement.setBytes(1, UniqueIdUtil.getBytesFromUUID(user.getUniqueId()));
      preparedStatement.setString(2, user.getName().get());
      preparedStatement.setString(3, user.getName().get());
      new DatabaseQueryImpl(this.executorService, preparedStatement).executeUpdate();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public void checkTable() {
    try (PreparedStatement preparedStatement = StatementFactory.getStatement("users-table")) {
      new DatabaseQueryImpl(this.executorService, preparedStatement).executeUpdate();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

}
