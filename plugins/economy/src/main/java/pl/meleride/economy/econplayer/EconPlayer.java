package pl.meleride.economy.econplayer;

import pl.meleride.economy.MelerideEconomy;
import pl.meleride.economy.currency.Currency;
import pl.meleride.economy.util.MathUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EconPlayer {

  private final UUID uuid;

  private final Map<Currency, Double> pocketBalance;

  private String name;

  public EconPlayer(UUID uuid, String name, MelerideEconomy melerideEconomy) {
    this.uuid = uuid;
    this.name = name;
    this.pocketBalance = new HashMap<>();

    add(Currency.PLN, 0.0);
    melerideEconomy.getEconPlayerManager().addPlayer(this);
  }

  public UUID getUuid() {
    return uuid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Map<Currency, Double> getPocketBalance() {
    return pocketBalance;
  }

  public boolean has(Currency currency, double amount) {
    return pocketBalance.getOrDefault(currency, 0.0) >= amount;
  }

  public boolean exchange(Currency from, Currency to, double amount) {
    if (this.pocketBalance.get(from) == null || this.pocketBalance.get(from) == 0.0) {
      return false;
    }

    if (amount <= 0 || amount > this.pocketBalance.get(from)) {
      return false;
    }

    double result = (amount * from.getExchangeRate()) / to.getExchangeRate();
    double newToBalance = MathUtils.round(this.pocketBalance.getOrDefault(to, 0.0) + result, 2);

    this.pocketBalance.put(from, this.pocketBalance.get(from) - amount);
    this.pocketBalance.put(to, newToBalance);

    return true;
  }

  public boolean charge(Currency currency, double amount) {
    if (!has(currency, amount)) {
      return false;
    }

    this.pocketBalance.put(currency, this.pocketBalance.get(currency) - amount);
    return true;
  }

  public void add(Currency currency, double amount) {
    this.pocketBalance.put(
            currency,
            this.pocketBalance.getOrDefault(currency, 0.0) + amount
    );
  }

}
