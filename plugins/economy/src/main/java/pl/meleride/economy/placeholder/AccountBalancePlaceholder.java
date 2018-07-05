package pl.meleride.economy.placeholder;

import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import be.maximvdw.placeholderapi.PlaceholderReplacer;
import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.api.MelerideAPI;
import pl.meleride.api.user.User;
import pl.meleride.api.user.accident.UserAccidentor;
import pl.meleride.economy.MelerideEconomy;
import pl.meleride.api.economy.currency.Currency;

public class AccountBalancePlaceholder implements PlaceholderReplacer {

  private final MelerideAPI api = JavaPlugin.getPlugin(MelerideAPI.class);
  private final MelerideEconomy plugin;
  private final Currency currency;

  public AccountBalancePlaceholder(MelerideEconomy plugin, Currency currency) {
    this.plugin = plugin;
    this.currency = currency;
  }

  @Override
  public String onPlaceholderReplace(PlaceholderReplaceEvent event) {

    if (event.getOfflinePlayer() == null || event.getPlayer() == null) {
      return "0.0 " + currency.getSign();
    }

    if (!this.api.getUserManager().getUser(event.getPlayer()).isPresent()) {
      UserAccidentor.notFoundOnManager(event.getPlayer());
    }

     User user = this.api.getUserManager().getUser(event.getPlayer()).get();

    return String.valueOf(user.getCurrencyBalance(currency)) + " " + currency.getSign();
  }

}
