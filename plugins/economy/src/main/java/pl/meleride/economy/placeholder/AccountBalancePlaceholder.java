package pl.meleride.economy.placeholder;

import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import be.maximvdw.placeholderapi.PlaceholderReplacer;
import pl.meleride.economy.MelerideEconomy;
import pl.meleride.economy.currency.Currency;
import pl.meleride.economy.user.EconomyUser;

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
      return "0.0 " + currency.getSign();
    }

    if (!this.plugin.getManager().getUser(event.getPlayer()).isPresent()) {
      BaseAccidentor accidentor = this.plugin.getAccidentor();
      accidentor.notFoundOnManager(event.getPlayer());
    }

     EconomyUser user = this.plugin.getManager().getUser(event.getPlayer()).get();

    return String.valueOf(user.getCurrencyBalance(currency)) + " " + currency.getSign();
  }

}
