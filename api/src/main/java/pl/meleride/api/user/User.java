package pl.meleride.api.user;

import java.time.temporal.TemporalAccessor;
import java.util.Map;
import java.util.Set;

import pl.meleride.api.economy.currency.Currency;
import pl.meleride.api.flexible.PlayableUser;
import pl.meleride.api.user.status.DiseaseStatus;

public interface User extends PlayableUser {

  // [ BASE ]
  void setName(String name);

  // [ DISEASES ]
  boolean hasAnyDisease();

  boolean hasDisease(DiseaseStatus diseaseStatus);

  Set<DiseaseStatus> getDiseases();

  void addDisease(DiseaseStatus disease);

  void removeDisease(DiseaseStatus disease);

  // [ ECONOMY ]

  Map<Currency, Double> getPocketBalance();

  double getCurrencyBalance(Currency currency);

  boolean has(Currency currency, double amount);

  boolean exchange(Currency from, Currency to, double amount);

  boolean charge(Currency currency, double amount);

  void add(Currency currency, double amount);

}
