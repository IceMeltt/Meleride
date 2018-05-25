package pl.meleride.api.message;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public final class MessageUtil {

  public static String colored(String message) {
    return message != null ? ChatColor.translateAlternateColorCodes('&', message) : null;
  }

  public static List<String> colored(List<String> messages) {
    List<String> colored = new ArrayList<>();
    messages.forEach(message -> colored.add(colored(message)));
    return colored;
  }

  private MessageUtil() {}

}
