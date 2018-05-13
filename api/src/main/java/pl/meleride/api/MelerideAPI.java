package pl.meleride.api;

import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.api.impl.manager.UserManagerImpl;
import pl.meleride.api.manager.UserManager;
import pl.meleride.commands.Commands;
import pl.meleride.commands.bukkit.BukkitCommands;

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
