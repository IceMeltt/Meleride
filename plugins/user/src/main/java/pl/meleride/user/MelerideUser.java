package pl.meleride.user;

import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.api.user.manager.UserManager;
import pl.meleride.api.user.manager.UserManagerImpl;
import pl.meleride.commands.Commands;
import pl.meleride.commands.bukkit.BukkitCommands;
import pl.meleride.user.impl.commands.ReputationCommand;

public class MelerideUser extends JavaPlugin {

  private UserManager userManager;

  @Override
  public void onEnable()  {
    userManager = new UserManagerImpl();

    Commands commands = new BukkitCommands(this);
    commands.registerCommandObject(new ReputationCommand(this));
  }

  @Override
  public void onDisable() {

  }

  public UserManager getUserManager() { return userManager; }

}
