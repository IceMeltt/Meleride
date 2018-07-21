package pl.meleride.user;

import com.zaxxer.hikari.HikariConfig;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.api.PluginModule;
import pl.meleride.api.i18n.MessageBundler;
import pl.meleride.api.storage.Resource;
import pl.meleride.api.storage.sql.SqlStorage;
import pl.meleride.api.storage.sql.hikari.SqlHikariStorage;
import pl.meleride.commands.Commands;
import pl.meleride.commands.bukkit.BukkitCommands;
import pl.meleride.user.commands.ReputationCommand;
import pl.meleride.user.entity.User;
import pl.meleride.user.listener.PlayerJoinListener;
import pl.meleride.user.listener.PlayerPreLoginListener;
import pl.meleride.user.listener.PlayerQuitListener;
import pl.meleride.user.manager.UserManager;
import pl.meleride.user.manager.UserManagerImpl;
import pl.meleride.user.resource.UserResourceImpl;

public class MelerideUser extends JavaPlugin implements PluginModule {

  private UserManager userManager;
  private Resource<User> userResource;
  private SqlStorage storage;
  private Commands commands;

  @Override
  public void onEnable() {
    this.userManager = new UserManagerImpl();
    this.userResource = new UserResourceImpl(this);
    this.storage = new SqlHikariStorage(this.dataSourceConfiguration());

    this.userResource.checkTable();

    this.registerListeners(
        new PlayerJoinListener(this),
        new PlayerPreLoginListener(this),
        new PlayerQuitListener(this)
    );

    this.commands = new BukkitCommands(this);
    this.commands.registerCommandObjects(new ReputationCommand(this));
  }

  @Override
  public void onDisable() {
    this.getServer().getOnlinePlayers().stream()
        .map(user -> this.getUserManager().getUser(user).get())
        .forEach(user -> this.getUserResource().save(user));
  }

  private HikariConfig dataSourceConfiguration() {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(MessageBundler.create("database.jdbc").toString());

    return config;
  }

  private void registerListeners(Listener... listeners) {
    PluginManager pluginManager = Bukkit.getPluginManager();
    for (Listener listener : listeners) {
      pluginManager.registerEvents(listener, this);
    }
  }

  @Override
  public UserManager getUserManager() {
    return this.userManager;
  }

  @Override
  public SqlStorage getStorage() {
    return this.storage;
  }

  public Resource<User> getUserResource() {
    return this.userResource;
  }

  public Commands getCommands() {
    return this.commands;
  }

}
