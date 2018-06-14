package pl.meleride.objects;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.commands.Commands;
import pl.meleride.commands.bukkit.BukkitCommands;
import pl.meleride.objects.impl.commands.GiveCommand;
import pl.meleride.objects.impl.system.ItemRegistrator;
import pl.meleride.objects.items.other.drugs.Cannabis;
import pl.meleride.objects.items.other.drugs.Cocaine;
import pl.meleride.objects.items.other.drugs.Heroine;
import pl.meleride.objects.items.other.drugs.MDMA;

public class MelerideObjects extends JavaPlugin {

  private Map<UUID, Long> userUsing; //TODO wyrzucić to cholerstwo jak najszybciej :< - DB!
  private Commands commands;

  @Override
  public void onEnable() {
    userUsing = new HashMap<>();

    getLogger().info("Rejestrowanie itemow...");
    ItemRegistrator.register(new MDMA(),
        new Cannabis(),
        new Heroine(),
        new Cocaine());

    getLogger().info("Rejestrowanie komend...");
    this.commands = new BukkitCommands(this);
    this.commands.registerCommandObjects(new GiveCommand());
  }

  public Commands getCommands() {
    return this.commands;
  }

  public Map<UUID, Long> getUserUsing() {
    return userUsing;
  }

}
