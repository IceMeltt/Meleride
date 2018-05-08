package pl.meleride.economy.runnables;

import org.bukkit.scheduler.BukkitRunnable;
import pl.meleride.economy.MelerideEconomy;
import pl.meleride.economy.currency.Currency;

import java.io.IOException;
import java.util.Arrays;

public class CurrencyUpdaterRunnable extends BukkitRunnable {

  private final MelerideEconomy plugin;

  public CurrencyUpdaterRunnable(MelerideEconomy plugin) {
    this.plugin = plugin;

    runTaskTimerAsynchronously(this.plugin, 0, 300*20);
  }

  @Override
  public void run() {
    Arrays.stream(Currency.values())
            .forEach(currency -> {
              this.plugin.getLogger().info("Aktualizowanie kursu waluty " + currency.getFullName());

              try {
                currency.updateExchangeRate();
              } catch(IOException e) {
                this.plugin.getLogger().severe("Cos poszlo nie tak przy aktualizowaniu kursu waluty " + currency.getFullName());
                e.printStackTrace();
                return;
              }

              this.plugin.getLogger().info("Pomyslnie zaktualizowano kurs waluty " + currency.getFullName());
            });
  }
}
