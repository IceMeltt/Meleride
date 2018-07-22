package pl.meleride.api.helper.provider;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.meleride.api.helper.Provider;

public class PlayerLocationProvider implements Provider<Location> {

  private final Player player;

  public PlayerLocationProvider(Player player) {
    this.player = player;
  }

  @Override
  public boolean isAvailable() {
    return this.player.isOnline();
  }

  @Override
  public Location get() {
    return this.player.getLocation();
  }

}
