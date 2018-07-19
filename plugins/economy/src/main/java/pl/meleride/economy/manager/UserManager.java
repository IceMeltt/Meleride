package pl.meleride.economy.manager;

import pl.meleride.economy.currency.Currency;
import pl.meleride.economy.entity.User;

public interface UserManager extends pl.meleride.api.manager.UserManager<User> {

  boolean has(User user, Currency currency, double amount);

  boolean exchange(User user, Currency from, Currency to, double amount);

  boolean charge(User user, Currency currency, double amount);

  void add(User user, Currency currency, double amount);

}
