package pl.meleride.economy.currencysign;

import org.bukkit.Location;
import org.bukkit.block.Sign;
import pl.meleride.economy.MelerideEconomy;
import pl.meleride.economy.currency.Currency;
import pl.meleride.economy.util.ColorUtils;

import java.util.HashMap;
import java.util.Map;

public class CurrencySign {

  private final Location signLocation;
  private final Currency currency;

  public CurrencySign(Location signLocation, Currency currency, MelerideEconomy melerideEconomy) {
    this.signLocation = signLocation;
    this.currency = currency;

    melerideEconomy.getCurrencySignManager().addCurrencySign(this);
  }

  public Currency getCurrency() {
    return currency;
  }

  public Location getSignLocation() {
    return signLocation;
  }

  public void update(MelerideEconomy melerideEconomy) {
    if (!(this.signLocation.getBlock().getState() instanceof Sign)) {
      delete(melerideEconomy);
      return;
    }

    Sign sign = (Sign) this.signLocation.getBlock().getState();

    sign.setLine(0, ColorUtils.colorize("&9&lWALUTA"));
    sign.setLine(1, this.currency.getFullName());
    sign.setLine(2, ColorUtils.colorize(this.currency.getExchangeRate() + " " + this.currency.getTendency().getSign()));

    sign.update();
  }

  public void delete(MelerideEconomy melerideEconomy) {
    melerideEconomy.getCurrencySignManager().removeCurrencySign(this);
  }

}
