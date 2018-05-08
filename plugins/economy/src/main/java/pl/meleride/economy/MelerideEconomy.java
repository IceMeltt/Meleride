package pl.meleride.economy;

import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.economy.commands.CurrencyCommand;
import pl.meleride.economy.runnables.CurrencyUpdaterRunnable;

public class MelerideEconomy extends JavaPlugin {

  private CurrencyUpdaterRunnable currencyUpdaterRunnable;

  @Override
  public void onEnable() {
    currencyUpdaterRunnable = new CurrencyUpdaterRunnable(this);

    getCommands().registerCommandObjects(new CurrencyCommand(this));
  }
}
