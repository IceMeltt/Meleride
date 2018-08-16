package pl.meleride.companies;

import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.api.helper.Listener;
import pl.meleride.api.storage.Resource;
import pl.meleride.api.storage.StorageException;
import pl.meleride.api.storage.sql.hikari.SqlHikariStorage;
import pl.meleride.commands.bukkit.BukkitCommands;
import pl.meleride.companies.command.CompanyCommand;
import pl.meleride.companies.entity.User;
import pl.meleride.companies.helper.HikariConfigurationHelper;
import pl.meleride.companies.listener.PlayerJoinListener;
import pl.meleride.companies.listener.PlayerPreLoginListener;
import pl.meleride.companies.listener.PlayerQuitListener;
import pl.meleride.companies.manager.UserManager;
import pl.meleride.companies.manager.impl.UserManagerImpl;
import pl.meleride.companies.resource.UserResource;
import pl.socketbyte.opengui.OpenGUI;

public final class MelerideCompaniesPlugin extends JavaPlugin implements MelerideCompanies {

  private UserManager userManager;
  private Resource<User> userResource;
  private SqlHikariStorage storage;

  @Override
  public void onLoad() {
    this.userManager = new UserManagerImpl();
    this.userResource = new UserResource(this);
    this.storage = new SqlHikariStorage(HikariConfigurationHelper.getDataSourceConfiguration());
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

    this.saveDefaultConfig();
    this.getConfig().options().copyDefaults(true);
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

  @Override
  public UserManager getUserManager() {
    return this.userManager;
  }

  @Override
  public Resource<User> getUserResource() {
    return this.userResource;
  }

  @Override
  public SqlHikariStorage getStorage() {
    return this.storage;
  }

}
