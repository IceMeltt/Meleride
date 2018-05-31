package pl.meleride.api.storage;

import org.apache.commons.lang.Validate;
import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.api.MelerideAPI;
import pl.meleride.api.exception.UnknownStatementTypeException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public final class StatementFactory {

  private static final MelerideAPI PLUGIN;
  private static final String USERS_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS `players` (`uuid` BINARY(16) PRIMARY KEY, `name` VARCHAR(16) NOT NULL UNIQUE)";
  private static final String PLAYERS_INSERT_QUERY = "INSERT INTO `players` VALUES (?, ?) ON DUPLICATE KEY UPDATE `name` = ?";
  private static final String PLAYERS_SELECT_QUERY = "SELECT * FROM `players`";
  private static final String PLAYERS_DELETE_QUERY = "DELETE FROM `players` WHERE `uuid` = ?";

  static {
    PLUGIN = JavaPlugin.getPlugin(MelerideAPI.class);
  }

  public static PreparedStatement getStatement(String preparedStatement) {
    Validate.notNull(preparedStatement, "Prepared statement name cannot be null!");

    Connection connection = PLUGIN.getDatabase().getConnection();
    Optional<PreparedStatement> result = Optional.empty();
    try {

      if (preparedStatement.equalsIgnoreCase("players-select")) {
        result = Optional.of(connection.prepareStatement(PLAYERS_SELECT_QUERY));
      } else if (preparedStatement.equalsIgnoreCase("players-insert")) {
        result = Optional.of(connection.prepareStatement(PLAYERS_INSERT_QUERY));
      } else if (preparedStatement.equalsIgnoreCase("players-delete")) {
        result = Optional.of(connection.prepareStatement(PLAYERS_DELETE_QUERY));
      } else if (preparedStatement.equalsIgnoreCase("users-table")) {
        result = Optional.of(connection.prepareStatement(USERS_TABLE_QUERY));
      }

    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    return result.orElseThrow(UnknownStatementTypeException::new);
  }

  private StatementFactory() {}

}
