package pl.meleride.economy.placeholder;

import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import be.maximvdw.placeholderapi.PlaceholderReplacer;
import pl.meleride.economy.MelerideEconomy;
import pl.meleride.economy.currency.Currency;
import pl.meleride.economy.entity.User;

public class AccountBalancePlaceholder implements PlaceholderReplacer {

  private final MelerideEconomy plugin;
  private final Currency currency;

  public AccountBalancePlaceholder(MelerideEconomy plugin, Currency currency) {
    this.plugin = plugin;
    this.currency = currency;
  }

  @Override
  public String onPlaceholderReplace(PlaceholderReplaceEvent event) {

    if (event.getOfflinePlayer() == null || event.getPlayer() == null) {
      return "0.0 " + this.currency.getSign();
    }

     User user = this.plugin.getUserManager().getUser(event.getPlayer()).get();

    return String.valueOf(user.getCurrencyBalance(this.currency)) + " " + this.currency.getSign();
  }

}
