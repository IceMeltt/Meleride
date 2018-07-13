package pl.meleride.economy.user;

import java.util.Map;
import pl.meleride.economy.currency.Currency;

public interface EconomyUser extends PlayableUser {

  Map<Currency, Double> getPocketBalance();

  double getCurrencyBalance(Currency currency);

  boolean has(Currency currency, double amount);

  boolean exchange(Currency from, Currency to, double amount);

  boolean charge(Currency currency, double amount);

  void add(Currency currency, double amount);

}
