package pl.meleride.api.effects;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface PlayerPlayable {
  
  void play(Player player, Location location);
  
}
