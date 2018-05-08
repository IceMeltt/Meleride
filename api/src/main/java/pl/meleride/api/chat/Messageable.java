package pl.meleride.api.chat;

import org.bukkit.ChatColor;

public interface Messageable {

  default String colorize(String text) {
    return ChatColor.translateAlternateColorCodes('&', text);
  }
}
