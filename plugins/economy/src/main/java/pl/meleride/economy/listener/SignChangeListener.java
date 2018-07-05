package pl.meleride.economy.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import pl.meleride.economy.MelerideEconomy;
import pl.meleride.api.economy.currency.Currency;
import pl.meleride.economy.currencysign.CurrencySign;

public class SignChangeListener implements Listener {

  private final MelerideEconomy plugin;

  public SignChangeListener(MelerideEconomy plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onSignChange(SignChangeEvent event) {
    Player player = event.getPlayer();

    if (!player.hasPermission("meleride.economy.signcurrency")) {
      return;
    }

    if (!event.getLine(0).equalsIgnoreCase("[waluta]")) {
      return;
    }

    if (Currency.getCurrency(event.getLine(1)) == null) {
      return;
    }

    event.setCancelled(true);

    new CurrencySign(event.getBlock().getLocation(),
            Currency.getCurrency(event.getLine(1)),
            this.plugin).update(this.plugin);
  }

}
