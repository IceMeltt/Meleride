package pl.meleride.economy.entity;

import java.util.Map;
import pl.meleride.economy.currency.Currency;

public interface User extends pl.meleride.api.entity.User {

  Map<Currency, Double> getPocketBalance();

  double getCurrencyBalance(Currency currency);

}
