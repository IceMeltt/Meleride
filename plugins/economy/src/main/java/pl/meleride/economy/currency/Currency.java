package pl.meleride.economy.currency;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import pl.meleride.api.util.MathUtils;
import pl.meleride.api.util.URLUtils;

import java.io.IOException;

public enum Currency {

  PLN("Zl", "Złoty polski", true, 1.0),
  EUR("€", "Euro", false, 4.28),
  USD("$", "Dolar amerykański", false, 3.60),
  CZK("Kč", "Korona czeska", false, 0.17),
  JPY("¥", "Jen", false, 0.03);

  private final String sign;
  private final String fullName;
  private final boolean isDefault;

  private final double defaultValue;

  private double exchangeRate;
  private double realExchangeRate;

  private double previousExchangeRate;

  private Tendency tendency;

  Currency(String sign, String fullName, boolean isDefault, double defaultValue) {
    this.sign = sign;
    this.fullName = fullName;
    this.isDefault = isDefault;

    this.defaultValue = defaultValue;

    this.exchangeRate = this.defaultValue;
    this.realExchangeRate = this.defaultValue;

    this.tendency = Tendency.STATIC;
  }

  public String getSign() {
    return sign;
  }

  public String getFullName() {
    return fullName;
  }

  public boolean isDefault() {
    return isDefault;
  }

  public double getExchangeRate() {
    return exchangeRate;
  }

  public double getRealExchangeRate() {
    return realExchangeRate;
  }

  public Tendency getTendency() {
    return tendency;
  }

  public void updateExchangeRate() throws IOException {
    if (this.isDefault) {
      return;
    }

    try {
      JSONObject apiResponse = URLUtils.getJsonUrlContent("http://api.nbp.pl/api/exchangerates/rates/A/" + this.name() + "/?format=json");

      this.previousExchangeRate = this.realExchangeRate;

      this.realExchangeRate = apiResponse.getJSONArray("rates")
              .getJSONObject(0)
              .getDouble("mid");
      this.exchangeRate = MathUtils.round(this.realExchangeRate, 2);
    } catch (UnirestException ex) {
      this.previousExchangeRate = this.realExchangeRate;
      this.exchangeRate = this.defaultValue;
    }

    if (this.previousExchangeRate > this.realExchangeRate) {
      this.tendency = Tendency.DECREASE;
    } else if (this.realExchangeRate > this.previousExchangeRate) {
      this.tendency = Tendency.INCREASE;
    } else {
      this.tendency = Tendency.STATIC;
    }
  }

  public static Currency getCurrency(String name) {
    for (Currency currency : Currency.values()) {
      if (currency.name().equalsIgnoreCase(name)) {
        return currency;
      }
    }

    return null;
  }

}
