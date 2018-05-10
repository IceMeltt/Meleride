package pl.meleride.economy.currency;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import pl.meleride.economy.util.MathUtils;
import pl.meleride.economy.util.URLUtils;

import java.io.IOException;
import java.net.UnknownHostException;

public enum Currency {

  PLN("Zl", "Złoty polski", true, 1.0),
  EUR("€", "Euro", false, 4.28),
  USD("$", "Dolar amerykański", false, 3.60),
  CZK("Kč", "Korona czeska", false, 0.17),
  JPY("¥", "Jen", false, 0.03);

  private final String sign;
  private final String fullName;
  private final boolean isDefault;

  private double exchangeRate;
  private double realExchangeRate;

  private double previousExchangeRate;

  private double defaultValue;

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
      String apiResponse = URLUtils.getURLContent("http://api.nbp.pl/api/exchangerates/rates/A/" + this.name() + "/?format=json");

      Gson gson = new GsonBuilder().create();

      JsonObject jsonObject = gson.fromJson(apiResponse, JsonObject.class);

      this.previousExchangeRate = this.realExchangeRate;

      this.realExchangeRate = jsonObject.getAsJsonArray("rates").get(0).getAsJsonObject().get("mid").getAsDouble();
      this.exchangeRate = MathUtils.round(this.realExchangeRate, 2);
    } catch (UnknownHostException ex) {
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
