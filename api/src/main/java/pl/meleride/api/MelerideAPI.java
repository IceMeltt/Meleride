package pl.meleride.api;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.api.impl.listener.PlayerLoginListener;
import pl.meleride.api.impl.listener.PlayerPreLoginListener;
import pl.meleride.api.impl.listener.PlayerQuitListener;
import pl.meleride.api.impl.manager.UserManagerImpl;
import pl.meleride.api.manager.UserManager;
import pl.meleride.commands.Commands;
import pl.meleride.commands.bukkit.BukkitCommands;

public final class MelerideAPI extends JavaPlugin {

  private UserManager userManager;
  private Commands commands;

  @Override
  public void onEnable() {
    this.userManager = new UserManagerImpl(this);
    this.commands = new BukkitCommands(this);

    this.registerListeners(
        new PlayerPreLoginListener(this),
        new PlayerLoginListener(this),
        new PlayerQuitListener(this)
    );

    this.saveDefaultConfig();
  }

  private void registerListeners(Listener... listeners) {
    for (Listener listener : listeners) {
      this.getServer().getPluginManager().registerEvents(listener, this);
    }
  }

  public Commands getCommands() {
    return this.commands;
  }

  public UserManager getUserManager() {
    return this.userManager;
  }

}
