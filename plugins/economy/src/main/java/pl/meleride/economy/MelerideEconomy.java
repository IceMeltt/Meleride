package pl.meleride.economy;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.economy.command.CurrencyCommand;
import pl.meleride.economy.listener.BlockBreakListener;
import pl.meleride.economy.listener.PlayerJoinListener;
import pl.meleride.economy.listener.SignChangeListener;
import pl.meleride.economy.runnable.CurrencyUpdaterRunnable;

public class MelerideEconomy extends JavaPlugin {

  private CurrencyUpdaterRunnable currencyUpdaterRunnable;

  @Override
  public void onEnable() {
    this.currencyUpdaterRunnable = new CurrencyUpdaterRunnable(this);

    getCommands().registerCommandObjects(new CurrencyCommand(this));

    registerListeners(
            new PlayerJoinListener(),
            new SignChangeListener(),
            new BlockBreakListener()
    );
  }

  private void registerListeners(Listener... listeners) {
    PluginManager pluginManager = Bukkit.getPluginManager();
    for (Listener listener : listeners) {
      pluginManager.registerEvents(listener, this);
    }
  }
}
