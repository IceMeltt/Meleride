package pl.meleride.api;

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
    this.commands = new BukkitCommands(this);
    this.userManager = new UserManagerImpl();
  }

  private void registerListeners(Listener... listeners) {
    for (Listener listener : listeners) {
      this.getServer().getPluginManager().registerEvents(listener, this);
    }
  }
  
  public Commands getCommands() {
    return commands;
  }

  public UserManager getUserManager() {
    return userManager;
  }
  
}
