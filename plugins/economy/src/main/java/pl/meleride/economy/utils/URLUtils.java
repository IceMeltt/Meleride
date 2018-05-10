package pl.meleride.economy.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

public final class URLUtils {

  private URLUtils() {}

  public static String getURLContent(String url) throws IOException {
    URL apiUrl = new URL(url);
    URLConnection urlConnection = apiUrl.openConnection();

    BufferedReader bufferedReader = new BufferedReader(
            new InputStreamReader(urlConnection.getInputStream(), Charset.forName("UTF-8")));

    StringBuilder output = new StringBuilder();

    String line;
    while((line = bufferedReader.readLine()) != null) output.append(line);

    return output.toString();
  }

}
