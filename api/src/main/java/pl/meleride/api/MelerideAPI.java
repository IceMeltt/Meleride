package pl.meleride.api;

import ch.jalu.injector.Injector;
import ch.jalu.injector.InjectorBuilder;
import org.bukkit.Server;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
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
  private Injector injector;

  public MelerideAPI() {}

  @Override
  public void onEnable() {
    this.initialize();

    this.registerListeners(
        new PlayerPreLoginListener(),
        new PlayerLoginListener(),
        new PlayerQuitListener()
    );

    this.saveDefaultConfig();
  }

  private void initialize() {
    this.injector = new InjectorBuilder().addDefaultHandlers("pl.meleride").create();
    this.commands = new BukkitCommands(this);
    this.injector.register(MelerideAPI.class, this);
    this.injector.register(Server.class, this.getServer());
    this.userManager = this.injector.getSingleton(UserManagerImpl.class);
    this.injector.register(UserManager.class, this.userManager);
  }

  private void registerListeners(Listener... listeners) {
    for (Listener listener : listeners) {
      this.getServer().getPluginManager().registerEvents(listener, this);
    }
  }
  
}
