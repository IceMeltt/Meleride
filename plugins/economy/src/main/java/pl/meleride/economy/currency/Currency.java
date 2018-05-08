package pl.meleride.economy.currency;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import pl.meleride.economy.utils.MathUtils;
import pl.meleride.economy.utils.URLUtils;

import java.io.IOException;

public enum Currency {
  PLN("Zl", "Złoty polski", true),
  EUR("€", "Euro", false),
  USD("$", "Dolar amerykański", false),
  CZK("Kč", "Korona czeska", false),
  JPY("¥", "Jen", false);

  private final String sign;
  private final String fullName;
  private final boolean isDefault;

  private double exchangeRate;
  private double realExchangeRate;

  private double previousExchangeRate;

  private Tendency tendency;

  Currency(String sign, String fullName, boolean isDefault) {
    this.sign = sign;
    this.fullName = fullName;
    this.isDefault = isDefault;

    this.exchangeRate = 1.0;
    this.realExchangeRate = 1.0;

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
    if(this.isDefault) return;

    String apiResponse = URLUtils.getURLContent("http://api.nbp.pl/api/exchangerates/rates/A/" + this.name() + "/?format=json");

    Gson gson = new GsonBuilder().create();

    JsonObject jsonObject = gson.fromJson(apiResponse, JsonObject.class);

    this.previousExchangeRate = this.realExchangeRate;

    this.realExchangeRate = jsonObject.getAsJsonArray("rates").get(0).getAsJsonObject().get("mid").getAsDouble();
    this.exchangeRate = MathUtils.round(this.realExchangeRate, 2);

    if(this.previousExchangeRate > this.realExchangeRate) {
      this.tendency = Tendency.DECREASE;
    } else if(this.realExchangeRate > this.previousExchangeRate) {
      this.tendency = Tendency.INCREASE;
    } else {
      this.tendency = Tendency.STATIC;
    }
  }
}
