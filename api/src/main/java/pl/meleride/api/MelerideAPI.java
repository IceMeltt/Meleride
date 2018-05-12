package pl.meleride.api;

import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.commands.Commands;
import pl.meleride.commands.bukkit.BukkitCommands;

public final class MelerideAPI extends JavaPlugin {

  private Commands commands;

  @Override
  public void onEnable() {
    this.commands = new BukkitCommands(this);
  }

  public Commands getCommands() {
    return commands;
  }

}
