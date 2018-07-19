package pl.meleride.api.helper;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

public final class URLHelper {

  public static JSONObject getJsonUrlContent(String url) throws UnirestException {
    HttpResponse<JsonNode> response = Unirest.get(url).asJson();

    return response.getBody().getObject();
  }

  private URLHelper() {
  }

}
