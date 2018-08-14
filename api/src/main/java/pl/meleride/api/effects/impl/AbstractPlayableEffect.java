package pl.meleride.api.effects.impl;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.meleride.api.effects.PlayableEffect;

import java.util.Collection;

public abstract class AbstractPlayableEffect implements PlayableEffect {
  
  public void play(Location location) {
    Bukkit.getOnlinePlayers().stream()
        .filter((p) -> p.getLocation().distance(location) <= 50)
        .forEach((p) -> this.play(p, location));
  }
  
  public void play(Collection<? extends Player> players, Location location) {
    players.forEach((p) -> this.play(p, location));
  }
  
}
