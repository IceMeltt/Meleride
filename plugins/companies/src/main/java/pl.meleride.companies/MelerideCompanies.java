package pl.meleride.companies;

import com.zaxxer.hikari.HikariConfig;
import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.api.PluginModule;
import pl.meleride.api.helper.Listener;
import pl.meleride.api.i18n.MessageBundler;
import pl.meleride.api.storage.Resource;
import pl.meleride.api.storage.StorageException;
import pl.meleride.api.storage.sql.hikari.SqlHikariStorage;
import pl.meleride.commands.bukkit.BukkitCommands;
import pl.meleride.companies.command.CompanyCommand;
import pl.meleride.companies.entity.User;
import pl.meleride.companies.listener.PlayerJoinListener;
import pl.meleride.companies.listener.PlayerPreLoginListener;
import pl.meleride.companies.listener.PlayerQuitListener;
import pl.meleride.companies.manager.CompanyManager;
import pl.meleride.companies.manager.UserManager;
import pl.meleride.companies.manager.impl.CompanyManagerImpl;
import pl.meleride.companies.manager.impl.UserManagerImpl;
import pl.meleride.companies.resource.UserResource;
import pl.socketbyte.opengui.OpenGUI;

public final class MelerideCompanies extends JavaPlugin implements PluginModule {

  private UserManager userManager;
  private CompanyManager companyManager;
  private Resource<User> userResource;
  private SqlHikariStorage storage;

  @Override
  public void onLoad() {
    this.userManager = new UserManagerImpl();
    this.companyManager = new CompanyManagerImpl();
    this.userResource = new UserResource(this);
    this.storage = new SqlHikariStorage(this.dataSourceConfiguration());
  }

  @Override
  public void onEnable() {
    BukkitCommands bukkitCommands = new BukkitCommands(this);
    OpenGUI.INSTANCE.register(this);

    this.registerListeners(
        new PlayerPreLoginListener(this),
        new PlayerJoinListener(this),
        new PlayerQuitListener(this)
    );

    bukkitCommands.registerCommandObjects(new CompanyCommand(this));
  }

  @Override
  public void onDisable() {
    try {
      this.storage.close();
    } catch (StorageException e) {
      e.printStackTrace();
    }
  }

  private void registerListeners(Listener<?>... listeners) {
    for (Listener<?> listener : listeners) {
      this.getServer().getPluginManager().registerEvents(listener, this);
    }
  }

  private HikariConfig dataSourceConfiguration() {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(MessageBundler.create("database.jdbc").toString());
    return config;
  }

  @Override
  public UserManager getUserManager() {
    return this.userManager;
  }

  public CompanyManager getCompanyManager() {
    return this.companyManager;
  }

  public Resource<User> getUserResource() {
    return this.userResource;
  }

  @Override
  public SqlHikariStorage getStorage() {
    return this.storage;
  }

}
