package pl.meleride.api;

import java.util.Properties;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import pl.meleride.api.i18n.MessageBundler;
import pl.meleride.api.object.command.GiveCommand;
import pl.meleride.api.storage.StorageException;
import pl.meleride.api.storage.sql.hikari.SQLHikariStorage;
import pl.meleride.api.user.caller.PlayerLoginListener;
import pl.meleride.api.user.caller.PlayerPreLoginListener;
import pl.meleride.api.user.caller.PlayerQuitListener;
import pl.meleride.api.user.manager.UserManagerImpl;
import pl.meleride.api.user.manager.UserManager;

import pl.meleride.commands.Commands;
import pl.meleride.commands.bukkit.BukkitCommands;

public final class MelerideAPI extends JavaPlugin {

  private Commands commands;
  private UserManager userManager;
  private SQLHikariStorage storage;

  @Override
  public void onEnable() {
    this.initializeDatabase();
    this.initializeListeners();

    this.registerListeners(
        new PlayerPreLoginListener(this),
        new PlayerLoginListener(this),
        new PlayerQuitListener(this)
    );

    this.saveDefaultConfig();
  }

  private void initializeListeners() {
    this.commands = new BukkitCommands(this);
    this.commands.registerCommandObjects(new GiveCommand());

    this.userManager = new UserManagerImpl();
  }

  private void registerListeners(Listener... listeners) {
    for (Listener listener : listeners) {
      this.getServer().getPluginManager().registerEvents(listener, this);
    }
  }

  private void initializeDatabase() {
    Properties properties = new Properties();
    properties.put("jdbcUrl", MessageBundler.create("database.jdbc").toString());
    storage = new SQLHikariStorage(properties);

    StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS users(")
        .append("uuid CHAR(100) NOT NULL,")
        .append("name TEXT(36) NOT NULL,")
        .append("disease TEXT NOT NULL,")
        .append("primary key(uuid));");

    try {
      this.storage.update(sb.toString());
    } catch(StorageException e) {
      this.getLogger().severe("Wystąpił BARDZO POTĘŻNY błąd w tworzeniu tabeli!!1");
      e.printStackTrace();
    }
  }
  
  public Commands getCommands() {
    return commands;
  }

  public UserManager getUserManager() {
    return userManager;
  }

  public SQLHikariStorage getStorage() {
    return storage;
  }
  
}
