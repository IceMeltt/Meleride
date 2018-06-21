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

  private CurrencyUpdaterRunnable currencyUpdaterRunnable;
  private BukkitCommands bukkitCommands;

  @Override
  public void onEnable() {
    this.currencyUpdaterRunnable = new CurrencyUpdaterRunnable(this);

    this.bukkitCommands = new BukkitCommands(this);

    this.bukkitCommands.registerCommandObjects(new CurrencyCommand(this));

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
