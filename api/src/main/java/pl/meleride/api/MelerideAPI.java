package pl.meleride.api;

import java.util.Properties;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import pl.meleride.api.flexible.BaseManager;
import pl.meleride.api.i18n.MessageBundler;
import pl.meleride.api.object.command.GiveCommand;
import pl.meleride.api.storage.StorageException;
import pl.meleride.api.storage.dao.StorageDao;
import pl.meleride.api.storage.dao.UserDaoImpl;
import pl.meleride.api.storage.sql.hikari.SQLHikariStorage;
import pl.meleride.api.user.User;
import pl.meleride.api.user.accident.BaseAccidentor;
import pl.meleride.api.user.accident.UserAccidentor;
import pl.meleride.api.user.caller.PlayerLoginListener;
import pl.meleride.api.user.caller.PlayerQuitListener;
import pl.meleride.api.user.manager.UserManagerImpl;

import pl.meleride.commands.Commands;
import pl.meleride.commands.bukkit.BukkitCommands;

public class MelerideAPI extends JavaPlugin {

  private Commands commands;
  private BaseAccidentor accidentor;
  private BaseManager<User> userManager;
  private StorageDao<User> userDao;
  private SQLHikariStorage storage;

  @Override
  public void onEnable() {
    this.initializeDatabase();
    this.initializeListeners();

    accidentor = new UserAccidentor(this);
    userDao = new UserDaoImpl(this);

    this.registerListeners(
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
        .append("uuid CHAR(36) NOT NULL,")
        .append("name TEXT(16) NOT NULL,")
        .append("disease TEXT NOT NULL,")
        .append("dataError TINYINT(1) NOT NULL,")
        .append("primary key(uuid));");

    try {
      this.storage.update(sb.toString());
    } catch(StorageException e) {
      this.getLogger().severe("Wystąpił BARDZO POTĘŻNY błąd w tworzeniu tabeli!!1");
      e.printStackTrace();
    }
  }

  public BaseAccidentor getAccidentor() {
    return this.accidentor;
  }

  public Commands getCommands() {
    return commands;
  }

  public BaseManager<User> getUserManager() {
    return userManager;
  }

  public SQLHikariStorage getStorage() {
    return storage;
  }

  public StorageDao<User> getUserDao() {
    return userDao;
  }

}
