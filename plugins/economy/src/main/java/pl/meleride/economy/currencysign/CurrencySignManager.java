package pl.meleride.economy.currencysign;

import org.bukkit.Location;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CurrencySignManager {

  private static final Map<Location, CurrencySign> CURRENCY_SIGN_MAP = new HashMap<>();

  public static Collection<CurrencySign> getCurrencySigns() {
    return CURRENCY_SIGN_MAP.values();
  }

  public static void addCurrencySign(CurrencySign currencySign) {
    if (!CURRENCY_SIGN_MAP.containsKey(currencySign.getSignLocation())) {
      CURRENCY_SIGN_MAP.put(currencySign.getSignLocation(), currencySign);
    }
  }

  public static void removeCurrencySign(CurrencySign currencySign) {
    CURRENCY_SIGN_MAP.remove(currencySign.getSignLocation());
  }

  public static Optional<CurrencySign> getCurrencySign(Location location) {
    return Optional.of(CURRENCY_SIGN_MAP.getOrDefault(location, null));
  }

}
