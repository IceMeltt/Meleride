package pl.meleride.objects;

import ch.jalu.injector.Injector;
import ch.jalu.injector.InjectorBuilder;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.objects.impl.commands.GiveCommand;

public class MelerideObjects extends JavaPlugin {

  private Injector injector;

  @Override
  public void onEnable() {
    getLogger().info("Rejestrowanie injectora...");
    this.injector = new InjectorBuilder().addDefaultHandlers("pl.meleride.objects").create();
    this.injector.register(MelerideObjects.class, this);
    this.injector.register(Server.class, this.getServer());

    getLogger().info("Rejestrowanie itemow...");
    // TODO

    getLogger().info("Rejestrowanie komend...");
    getCommand("daj").setExecutor(injector.getSingleton(GiveCommand.class));
  }
}
