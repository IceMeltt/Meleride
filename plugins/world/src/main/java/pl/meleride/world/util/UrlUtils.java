package pl.meleride.world.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

public class UrlUtils {

  public static String getContent(String query) throws IOException {
    URL url = new URL(query);
    URLConnection urlConnection = url.openConnection();

    BufferedReader buffReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), Charset.forName("UTF-8")));

    StringBuilder output = new StringBuilder();

    String line;
    while ((line = buffReader.readLine()) != null) {
      output.append(line);
    }

    return output.toString();
  }

  private UrlUtils() {
  }

}
