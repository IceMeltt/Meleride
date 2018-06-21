package pl.meleride.economy.currencysign;

import org.bukkit.Location;
import org.bukkit.block.Sign;
import pl.meleride.economy.currency.Currency;
import pl.meleride.economy.util.ColorUtils;

import java.util.HashMap;
import java.util.Map;

public class CurrencySign {

  private final static Map<Location, CurrencySign> currencySignMap = new HashMap<>();

  private final Location signLocation;
  private final Currency currency;

  public CurrencySign(Location signLocation, Currency currency) {
    this.signLocation = signLocation;
    this.currency = currency;

    currencySignMap.put(signLocation, this);
  }

  public void update() {
    if (!(this.signLocation.getBlock().getState() instanceof Sign)) {
      delete();
      return;
    }

    Sign sign = (Sign) this.signLocation.getBlock().getState();

    sign.setLine(0, ColorUtils.colorize("&9&lWALUTA"));
    sign.setLine(1, this.currency.getFullName());
    sign.setLine(2, ColorUtils.colorize(this.currency.getExchangeRate() + " " + this.currency.getTendency().getSign()));

    sign.update();
  }

  public void delete() {
    currencySignMap.remove(this.signLocation);
  }

  public static Map<Location, CurrencySign> getCurrencySignMap() {
    return currencySignMap;
  }

  public static CurrencySign getCurrencySign(Location location) {
    return currencySignMap.getOrDefault(location, null);
  }

}
