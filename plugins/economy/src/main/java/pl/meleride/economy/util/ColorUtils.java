package pl.meleride.economy.util;

import org.bukkit.ChatColor;

public final class ColorUtils {

  public static String colorize(String text) {
    return ChatColor.translateAlternateColorCodes('&', text);
  }

  private ColorUtils() {
  }

}
