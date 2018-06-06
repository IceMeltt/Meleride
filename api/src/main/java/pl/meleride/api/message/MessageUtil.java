package pl.meleride.api.message;

import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;

import java.util.List;

public final class MessageUtil {

  public static String colored(String message) {
    return message != null ? ChatColor.translateAlternateColorCodes('&', message) : null;
  }

  public static List<String> colored(List<String> messages) {
    Validate.notEmpty(messages, "Messages cannot be empty!");

    return messages.stream()
        .map(MessageUtil::colored)
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }

  private MessageUtil() {}

}
