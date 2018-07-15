package pl.meleride.base;

import com.zaxxer.hikari.HikariConfig;
import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.api.PluginModule;
import pl.meleride.api.helper.Listener;
import pl.meleride.api.manager.UserManager;
import pl.meleride.api.storage.Resource;
import pl.meleride.api.storage.sql.SqlStorage;
import pl.meleride.api.storage.sql.hikari.SqlHikariStorage;
import pl.meleride.base.entity.User;
import pl.meleride.base.listener.PlayerJoinListener;
import pl.meleride.base.listener.PlayerPreLoginListener;
import pl.meleride.base.listener.PlayerQuitListener;
import pl.meleride.base.manager.UserManagerImpl;
import pl.meleride.base.resource.UserResourceImpl;

public class MelerideBase extends JavaPlugin implements PluginModule {

  private UserManager<User> userManager;
  private Resource<User> userResource;
  private SqlStorage storage;

  @Override
  public void onEnable() {
    this.userManager = new UserManagerImpl();
    this.userResource = new UserResourceImpl(this);
    this.storage = new SqlHikariStorage(this.dataSourceConfiguration());

    this.userResource.checkTable();
    this.userResource.load();

    this.registerListeners(
        new PlayerPreLoginListener(this),
        new PlayerJoinListener(this),
        new PlayerQuitListener(this)
    );
  }

  private void registerListeners(Listener<?>... listeners) {
    for (Listener<?> listener : listeners) {
      this.getServer().getPluginManager().registerEvents(listener, this);
    }
  }

  private HikariConfig dataSourceConfiguration() {
    HikariConfig config = new HikariConfig();

    config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
    config.addDataSourceProperty("serverName", "localhost");
    config.addDataSourceProperty("port", "3306");
    config.addDataSourceProperty("databaseName", "meleride");
    config.addDataSourceProperty("user", "admin");
    config.addDataSourceProperty("password", "root");

    return config;
  }

  @Override
  public UserManager<User> getUserManager() {
    return this.userManager;
  }

  @Override
  public SqlStorage getStorage() {
    return this.storage;
  }

  public Resource<User> getUserResource() {
    return userResource;
  }

}
