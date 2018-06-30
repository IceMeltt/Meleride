package pl.meleride.economy.placeholder;

import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import be.maximvdw.placeholderapi.PlaceholderReplacer;
import pl.meleride.economy.MelerideEconomy;
import pl.meleride.economy.currency.Currency;
import pl.meleride.economy.econplayer.EconPlayer;

import java.util.Optional;

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

    Optional<EconPlayer> optionalEconPlayer = this.plugin.getEconPlayerManager().getPlayer(event.getPlayer().getUniqueId());

    if (!optionalEconPlayer.isPresent()){
      return "0.0 " + currency.getSign();
    }

    EconPlayer econPlayer = optionalEconPlayer.get();

    return String.valueOf(econPlayer.getCurrencyBalance(currency)) + " " + currency.getSign();
  }

}
