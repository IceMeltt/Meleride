package pl.meleride.objects;

import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.commands.Commands;
import pl.meleride.commands.bukkit.BukkitCommands;
import pl.meleride.objects.impl.commands.GiveCommand;

public class MelerideObjects extends JavaPlugin {

  private Commands commands;

  @Override
  public void onEnable() {
    getLogger().info("Rejestrowanie itemow...");
    // TODO

    getLogger().info("Rejestrowanie komend...");
    this.commands = new BukkitCommands(this);
    this.commands.registerCommandObjects(new GiveCommand());
  }

  public Commands getCommands() {
    return this.commands;
  }

}
