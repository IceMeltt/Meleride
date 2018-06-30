package pl.meleride.economy.currencysign;

import org.bukkit.Location;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CurrencySignManager {

  private final Map<Location, CurrencySign> currencySignMap = new HashMap<>();

  public CurrencySignManager() {
  }

  public Collection<CurrencySign> getCurrencySigns() {
    return currencySignMap.values();
  }

  public void addCurrencySign(CurrencySign currencySign) {
    if (!currencySignMap.containsKey(currencySign.getSignLocation())) {
      currencySignMap.put(currencySign.getSignLocation(), currencySign);
    }
  }

  public void removeCurrencySign(CurrencySign currencySign) {
    currencySignMap.remove(currencySign.getSignLocation());
  }

  public Optional<CurrencySign> getCurrencySign(Location location) {
    return Optional.of(currencySignMap.getOrDefault(location, null));
  }

}
