package pl.meleride.world.impl.weather;

import org.bukkit.Bukkit;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import pl.meleride.api.i18n.MessageBundler;
import pl.meleride.api.message.MessageType;

public class Weather {

  private int actualTemp = -1;
  private String newForecast = "Brak informacji";
  private String olderForecast = "Brak informacji";

  private static final String UPDATE_URL = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22Warsaw%22)%20and%20u%3D'c'&format=json";

  public static String translate(String input) {
    return MessageBundler.create("forecast." + input).toString();
  }
  
  public static String getUpdateUrl() {
    return UPDATE_URL;
  }

  public int getTemperature() {
    return this.actualTemp;
  }

  public String getNewerForecast() {
    return this.newForecast;
  }

  public String getOlderForecast() {
    return this.olderForecast;
  }

  public void updateWeather() throws UnirestException {
    HttpResponse<JsonNode> response = Unirest.get(UPDATE_URL).asJson();

    JSONObject template = response.getBody().getObject()
        .getJSONObject("query")
        .getJSONObject("results")
        .getJSONObject("channel")
        .getJSONObject("item")
        .getJSONObject("condition");

    this.actualTemp = Integer.parseInt(template.get("temp").toString());

    this.olderForecast = this.newForecast;
    this.newForecast = template.get("text").toString();

    if (!this.newForecast.equalsIgnoreCase(this.olderForecast)) {
      MessageBundler.create("forecast.newforecast")
          .withField("FORECAST", this.newForecast)
          .target(MessageType.CHAT)
          .sendTo(Bukkit.getOnlinePlayers());
    }
  }

}
