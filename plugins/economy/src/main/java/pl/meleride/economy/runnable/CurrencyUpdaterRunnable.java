package pl.meleride.economy.runnable;

import org.bukkit.scheduler.BukkitRunnable;
import pl.meleride.economy.MelerideEconomy;
import pl.meleride.economy.currency.Currency;
import pl.meleride.economy.currencysign.CurrencySign;
import pl.meleride.economy.currencysign.CurrencySignManager;

import java.io.IOException;

public class CurrencyUpdaterRunnable extends BukkitRunnable {

  private final MelerideEconomy plugin;

  public CurrencyUpdaterRunnable(MelerideEconomy plugin) {
    this.plugin = plugin;

    runTaskTimerAsynchronously(this.plugin, 0, 1800 * 20);
  }

  @Override
  public void run() {
    for (Currency currency : Currency.values()) {
      this.plugin.getLogger().info("Aktualizowanie kursu waluty " + currency.getFullName());

      try {
        currency.updateExchangeRate();
      } catch (IOException e) {
        this.plugin.getLogger().severe("Cos poszlo nie tak przy aktualizowaniu kursu waluty " + currency.getFullName());
        e.printStackTrace();
        return;
      }
      this.plugin.getLogger().info("Pomyslnie zaktualizowano kurs waluty " + currency.getFullName());
    }

    CurrencySignManager.getCurrencySigns().forEach(CurrencySign::update);
  }

}
