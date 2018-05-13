package pl.meleride.api;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.api.impl.listener.PlayerJoinListener;
import pl.meleride.api.impl.listener.PlayerLoginListener;
import pl.meleride.api.impl.listener.PlayerPreLoginListener;
import pl.meleride.api.impl.manager.UserManagerImpl;
import pl.meleride.api.manager.UserManager;
import pl.meleride.commands.Commands;
import pl.meleride.commands.bukkit.BukkitCommands;

import java.util.Arrays;

public final class MelerideAPI extends JavaPlugin {

  private static MelerideAPI instance;

  private UserManager userManager;
  private Commands commands;

  public MelerideAPI() {
    instance = this;
  }

  @Override
  public void onEnable() {
    this.userManager = new UserManagerImpl();
    this.commands = new BukkitCommands(this);

    this.registerListeners(
        new PlayerPreLoginListener(this),
        new PlayerLoginListener(this),
        new PlayerJoinListener(this)
    );

  }

  private void registerListeners(Listener... listeners) {
    Arrays.stream(listeners).forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));
  }

  public Commands getCommands() {
    return this.commands;
  }

  public UserManager getUserManager() {
    return this.userManager;
  }

  public static MelerideAPI getInstance() {
    return instance;
  }

}
