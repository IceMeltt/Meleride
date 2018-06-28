package pl.meleride.economy;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.commands.bukkit.BukkitCommands;
import pl.meleride.economy.command.CurrencyCommand;
import pl.meleride.economy.currencysign.CurrencySignManager;
import pl.meleride.economy.econplayer.EconPlayerManager;
import pl.meleride.economy.listener.BlockBreakListener;
import pl.meleride.economy.listener.PlayerJoinListener;
import pl.meleride.economy.listener.SignChangeListener;
import pl.meleride.economy.runnable.CurrencyUpdaterRunnable;

public class MelerideEconomy extends JavaPlugin {

  private EconPlayerManager econPlayerManager;
  private CurrencySignManager currencySignManager;

  @Override
  public void onEnable() {
    econPlayerManager = new EconPlayerManager();
    currencySignManager = new CurrencySignManager();

    new CurrencyUpdaterRunnable(this);

    BukkitCommands bukkitCommands = new BukkitCommands(this);

    bukkitCommands.registerCommandObjects(new CurrencyCommand(this));

    registerListeners(
            new PlayerJoinListener(this),
            new SignChangeListener(this),
            new BlockBreakListener(this)
    );
  }

  @Override
  public void onDisable() {

  }

  public EconPlayerManager getEconPlayerManager() {
    return this.econPlayerManager;
  }

  public CurrencySignManager getCurrencySignManager() {
    return this.currencySignManager;
  }

  private void registerListeners(Listener... listeners) {
    PluginManager pluginManager = Bukkit.getPluginManager();
    for (Listener listener : listeners) {
      pluginManager.registerEvents(listener, this);
    }
  }

}
