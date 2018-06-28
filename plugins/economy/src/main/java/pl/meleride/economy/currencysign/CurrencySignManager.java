package pl.meleride.economy.currencysign;

import org.bukkit.Location;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CurrencySignManager {

  private final Map<Location, CurrencySign> CURRENCY_SIGN_MAP = new HashMap<>();

  public CurrencySignManager() {
  }

  public Collection<CurrencySign> getCurrencySigns() {
    return CURRENCY_SIGN_MAP.values();
  }

  public void addCurrencySign(CurrencySign currencySign) {
    if (!CURRENCY_SIGN_MAP.containsKey(currencySign.getSignLocation())) {
      CURRENCY_SIGN_MAP.put(currencySign.getSignLocation(), currencySign);
    }
  }

  public void removeCurrencySign(CurrencySign currencySign) {
    CURRENCY_SIGN_MAP.remove(currencySign.getSignLocation());
  }

  public Optional<CurrencySign> getCurrencySign(Location location) {
    return Optional.of(CURRENCY_SIGN_MAP.getOrDefault(location, null));
  }

}
