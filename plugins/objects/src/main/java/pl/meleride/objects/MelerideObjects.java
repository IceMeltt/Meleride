package pl.meleride.objects;

import ch.jalu.injector.Injector;
import ch.jalu.injector.InjectorBuilder;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.commands.Commands;
import pl.meleride.commands.bukkit.BukkitCommands;
import pl.meleride.objects.impl.commands.GiveCommand;

public class MelerideObjects extends JavaPlugin {

  private Injector injector;
  private Commands commands;

  @Override
  public void onEnable() {

    getLogger().info("Rejestrowanie injectora...");
    this.injector = new InjectorBuilder().addDefaultHandlers("pl.meleride.objects").create();
    this.injector.register(MelerideObjects.class, this);
    this.injector.register(Server.class, this.getServer());

    getLogger().info("Rejestrowanie itemow...");
    // TODO

    getLogger().info("Rejestrowanie komend...");
    this.commands = new BukkitCommands(this);
    this.commands.registerCommandObjects(this.injector.getSingleton(GiveCommand.class));
  }

  public Injector getInjector() {
    return this.injector;
  }

  public Commands getCommands() {
    return this.commands;
  }

}
