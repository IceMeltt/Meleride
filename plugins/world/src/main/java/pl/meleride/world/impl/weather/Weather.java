package pl.meleride.world.impl.weather;

import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import pl.meleride.world.impl.weather.events.WeatherChangedEvent;
import pl.meleride.world.impl.weather.types.WeatherType;

public class Weather {

  private int actualTemp = -1;
  private WeatherType newForecast = WeatherType.NOT_AVAILABLE;
  private WeatherType olderForecast = WeatherType.NOT_AVAILABLE;

  public static final String UPDATE_URL = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22Warsaw%22)%20and%20u%3D'c'&format=json";

  public int getTemperature() {
    return this.actualTemp;
  }

  public WeatherType getNewerForecast() {
    return this.newForecast;
  }

  public WeatherType getOlderForecast() {
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
    WeatherType weather = WeatherType.byCode(Integer.parseInt(template.get("code").toString()));
    Validate.notNull(weather, "Unrecognized weather with code " + template.get("code").toString() + "!");

    if (!weather.equals(this.newForecast)) {
      WeatherChangedEvent event = new WeatherChangedEvent(this.newForecast, weather);
      Bukkit.getPluginManager().callEvent(event);
      if (!event.isCancelled()) {
        this.olderForecast = this.newForecast;
        this.newForecast = weather;
        this.newForecast.setWeather();
      }
    }
  }

}
