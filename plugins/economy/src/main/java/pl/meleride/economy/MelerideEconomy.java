package pl.meleride.economy;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.commands.bukkit.BukkitCommands;
import pl.meleride.economy.command.CurrencyCommand;
import pl.meleride.economy.listener.BlockBreakListener;
import pl.meleride.economy.listener.PlayerJoinListener;
import pl.meleride.economy.listener.SignChangeListener;
import pl.meleride.economy.runnable.CurrencyUpdaterRunnable;

public class MelerideEconomy extends JavaPlugin {

  @Override
  public void onEnable() {
    new CurrencyUpdaterRunnable(this);

    BukkitCommands bukkitCommands = new BukkitCommands(this);

    bukkitCommands.registerCommandObjects(new CurrencyCommand());

    registerListeners(
            new PlayerJoinListener(),
            new SignChangeListener(),
            new BlockBreakListener()
    );
  }

  @Override
  public void onDisable() {

  }

  private void registerListeners(Listener... listeners) {
    PluginManager pluginManager = Bukkit.getPluginManager();
    for (Listener listener : listeners) {
      pluginManager.registerEvents(listener, this);
    }
  }

}
