package pl.meleride.api.storage;

import org.apache.commons.lang.Validate;
import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.api.MelerideAPI;
import pl.meleride.api.exception.UnknownDatabaseTypeException;

import java.util.Optional;

public final class DatabaseFactory {

  private static final MelerideAPI PLUGIN = JavaPlugin.getPlugin(MelerideAPI.class);

  public static Database getDatabase(String databaseName) {
    Validate.notNull(databaseName, "Database type's name cannot be null");

    Optional<Database> optionalDatabase = Optional.empty();
    if (databaseName.equalsIgnoreCase("mysql")) {
      optionalDatabase = Optional.of(new MysqlDatabase(
        PLUGIN.getExecutorService(),
        "meleride",
        "localhost",
        "root",
        "root",
        3306)); // until config will be implemented
    }

    return optionalDatabase.orElseThrow(UnknownDatabaseTypeException::new);
  }

  private DatabaseFactory() {}

}
