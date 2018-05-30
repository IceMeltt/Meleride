package pl.meleride.api.storage;

import org.apache.commons.lang.Validate;
import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.api.MelerideAPI;
import pl.meleride.api.exception.UnknownDatabaseTypeException;
import pl.meleride.api.i18n.MessageBundler;

import java.util.Optional;

public final class DatabaseFactory {

  private static final MelerideAPI PLUGIN = JavaPlugin.getPlugin(MelerideAPI.class);

  private static final String DATABASE_NAME;
  private static final String SERVER_NAME;
  private static final String USERNAME;
  private static final String PASSWORD;
  private static final int PORT;

  static {
    DATABASE_NAME = MessageBundler.create("database.mysql.databaseName").toString();
    SERVER_NAME = MessageBundler.create("database.mysql.serverName").toString();
    USERNAME = MessageBundler.create("database.mysql.username").toString();
    PASSWORD = MessageBundler.create("database.mysql.password").toString();
    PORT = Integer.valueOf(MessageBundler.create("database.mysql.port").toString());
  }

  public static Database getDatabase(String databaseName) {
    Validate.notNull(databaseName, "Database type's name cannot be null");

    Optional<Database> optionalDatabase = Optional.empty();
    if (databaseName.equalsIgnoreCase("mysql")) {
      optionalDatabase = Optional.of(new MysqlDatabase(
        PLUGIN.getExecutorService(),
        DATABASE_NAME,
        SERVER_NAME,
        USERNAME,
        PASSWORD,
        PORT));
    }

    return optionalDatabase.orElseThrow(UnknownDatabaseTypeException::new);
  }

  private DatabaseFactory() {}

}
