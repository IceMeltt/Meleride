package pl.meleride.economy.manager;

import pl.meleride.economy.currency.Currency;
import pl.meleride.economy.entity.User;

public interface UserManager extends pl.meleride.api.manager.UserManager<User> {

  boolean hasMoney(User user, Currency currency, double amount);

  boolean exchangeMoney(User user, Currency from, Currency to, double amount);

  boolean chargeMoney(User user, Currency currency, double amount);

  void addMoney(User user, Currency currency, double amount);

}
