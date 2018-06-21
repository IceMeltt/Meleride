package pl.meleride.economy.util;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

public final class URLUtils {

  //Powinno zostac przeniesione w przyszlosci do melerideapi
  public static JSONObject getJsonUrlContent(String url) throws UnirestException {
    HttpResponse<JsonNode> response = Unirest.get(url).asJson();

    return response.getBody().getObject();
  }

  private URLUtils() {
  }

}
