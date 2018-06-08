package pl.meleride.world.impl.weather;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.IOException;
import pl.meleride.api.i18n.MessageBundler;
import pl.meleride.world.util.UrlUtils;

import static pl.meleride.api.message.MessageUtil.colored;

public class Weather {

  private int actualTemp;
  private String forecast;

  public static String translate(String input) {
    return colored(MessageBundler.create("forecast." + input).toString());
  }

  public int getTemperature() {
    return this.actualTemp;
  }

  public String getForecast() {
    return this.forecast;
  }

  public void updateWeather() throws IOException {

    String response = UrlUtils.getContent("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22Warsaw%22)%20and%20u%3D'c'&format=json");
    Gson gson = new GsonBuilder().create();
    JsonObject json = gson.fromJson(response, JsonObject.class);

    JsonObject template = json.get("query")
        .getAsJsonObject().get("results")
        .getAsJsonObject().get("channel")
        .getAsJsonObject().get("item")
        .getAsJsonObject().get("condition")
        .getAsJsonObject();

    this.actualTemp = Integer.parseInt(template.get("temp").getAsString());

    this.forecast = template.get("text").getAsString();
  }

}
