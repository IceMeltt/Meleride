package pl.meleride.economy.currencysign;

import org.bukkit.Location;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CurrencySignManager {

  private final Map<Location, CurrencySign> currencySignMap = new HashMap<>();

  public Collection<CurrencySign> getCurrencySigns() {
    return this.currencySignMap.values();
  }

  public void addCurrencySign(CurrencySign currencySign) {
    if (!this.currencySignMap.containsKey(currencySign.getSignLocation())) {
      this.currencySignMap.put(currencySign.getSignLocation(), currencySign);
    }
  }

  public void removeCurrencySign(CurrencySign currencySign) {
    this.currencySignMap.remove(currencySign.getSignLocation());
  }

  public Optional<CurrencySign> getCurrencySign(Location location) {
    return Optional.of(this.currencySignMap.getOrDefault(location, null));
  }

}
