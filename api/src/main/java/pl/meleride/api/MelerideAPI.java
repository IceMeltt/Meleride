package pl.meleride.api;

import ch.jalu.injector.Injector;
import ch.jalu.injector.InjectorBuilder;
import org.bukkit.Server;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.api.storage.Database;
import pl.meleride.api.storage.DatabaseFactory;
import pl.meleride.api.storage.resource.Resource;
import pl.meleride.api.storage.resource.UserResourceImpl;
import pl.meleride.api.user.User;
import pl.meleride.api.user.caller.PlayerLoginListener;
import pl.meleride.api.user.caller.PlayerPreLoginListener;
import pl.meleride.api.user.caller.PlayerQuitListener;
import pl.meleride.api.user.manager.UserManagerImpl;
import pl.meleride.api.user.manager.UserManager;
import pl.meleride.commands.Commands;
import pl.meleride.commands.bukkit.BukkitCommands;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class MelerideAPI extends JavaPlugin {

  private ExecutorService executorService;
  private UserManager userManager;
  private Injector injector;
  private Commands commands;
  private Database database;
  private Resource<User> userResource;

  public MelerideAPI() {
  }

  @Override
  public void onEnable() {
    this.initialize();

    this.registerListeners(
      PlayerPreLoginListener.class,
      PlayerLoginListener.class,
      PlayerQuitListener.class
    );

    this.getConfig().options().copyDefaults(true);
    this.saveDefaultConfig();
  }

  private void initialize() {
    this.injector = new InjectorBuilder().addDefaultHandlers("pl.meleride").create();
    this.commands = new BukkitCommands(this);

    this.executorService = Executors.newFixedThreadPool(3);

    this.injector.register(ExecutorService.class, this.executorService);
    this.injector.register(MelerideAPI.class, this);
    this.injector.register(Server.class, this.getServer());
    this.userManager = this.injector.getSingleton(UserManagerImpl.class);
    this.injector.register(UserManager.class, this.userManager);

    this.database = DatabaseFactory.getDatabase("mysql");

    this.userResource = this.injector.getSingleton(UserResourceImpl.class);
    this.userResource.checkTable();
    this.userResource.load();
    this.injector.register(Resource.class, this.userResource);
  }

  @SafeVarargs
  private final void registerListeners(Class<? extends Listener>... listeners) {
    for (Class<? extends Listener> listener : listeners) {
      this.getServer().getPluginManager().registerEvents(this.injector.getSingleton(listener), this);
    }
  }

  public ExecutorService getExecutorService() {
    return this.executorService;
  }

  public Database getDatabase() {
    return this.database;
  }

}
