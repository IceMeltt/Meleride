package pl.meleride.economy.utils;

import org.bukkit.ChatColor;

public final class ColorUtils {

  private ColorUtils() {}

  public static String colorize(String text) {
    return ChatColor.translateAlternateColorCodes('&', text);
  }

}
