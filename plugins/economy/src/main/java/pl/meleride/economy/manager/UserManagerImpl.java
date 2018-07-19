package pl.meleride.economy.manager;

import pl.meleride.api.helper.MathHelper;
import pl.meleride.api.manager.AbstractUserManager;
import pl.meleride.economy.currency.Currency;
import pl.meleride.economy.entity.User;

public class UserManagerImpl extends AbstractUserManager<User> implements UserManager {

  public boolean has(User user, Currency currency, double amount) {
    return user.getPocketBalance().getOrDefault(currency, 0.0) >= amount;
  }

  public boolean exchange(User user, Currency from, Currency to, double amount) {
    if (user.getPocketBalance().get(from) == null || user.getPocketBalance().get(from) == 0.0) {
      return false;
    }

    if (amount <= 0 || amount > user.getPocketBalance().get(from)) {
      return false;
    }

    double result = (amount * from.getExchangeRate()) / to.getExchangeRate();
    double newToBalance = MathHelper.round(user.getPocketBalance().getOrDefault(to, 0.0) + result, 2);

    user.getPocketBalance().put(from, user.getPocketBalance().get(from) - amount);
    user.getPocketBalance().put(to, newToBalance);

    return true;
  }

  public boolean charge(User user, Currency currency, double amount) {
    if (!this.has(user, currency, amount)) {
      return false;
    }

    user.getPocketBalance().put(currency, user.getPocketBalance().get(currency) - amount);
    return true;
  }

  public void add(User user, Currency currency, double amount) {
    user.getPocketBalance().put(
        currency,
        user.getPocketBalance().getOrDefault(currency, 0.0) + amount
    );
  }

}
