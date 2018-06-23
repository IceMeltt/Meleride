package pl.meleride.api;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.api.object.command.GiveCommand;
import pl.meleride.api.user.caller.PlayerLoginListener;
import pl.meleride.api.user.caller.PlayerPreLoginListener;
import pl.meleride.api.user.caller.PlayerQuitListener;
import pl.meleride.api.user.manager.UserManager;
import pl.meleride.api.user.manager.UserManagerImpl;
import pl.meleride.commands.Commands;
import pl.meleride.commands.bukkit.BukkitCommands;

public final class MelerideAPI extends JavaPlugin {

  private Commands commands;
  private UserManager userManager;

  private boolean usingHolograms;

  @Override
  public void onEnable() {
    this.initialize();

    this.registerListeners(
        new PlayerPreLoginListener(),
        new PlayerLoginListener(),
        new PlayerQuitListener()
    );

    this.saveDefaultConfig();

    this.usingHolograms = Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays");
    if (!usingHolograms) {
      this.getLogger()
          .warning("Plugin HolographicDisplays not found! Hologram messages will not work!");
    }
  }

  private void initialize() {
    this.commands = new BukkitCommands(this);
    this.commands.registerCommandObjects(new GiveCommand());

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

  public boolean isUsingHolograms() {
    return usingHolograms;
  }

}
