package pl.meleride.api.helper.provider;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import pl.meleride.api.helper.Provider;

public class WorldLocationProvider implements Provider<Location> {

  private final Location location;

  public WorldLocationProvider(Location location) {
    this.location = location;
  }

  @Override
  public boolean isAvailable() {
    return Bukkit.getWorlds().contains(location.getWorld());
  }

  @Override
  public Location get() {
    return this.location;
  }

}
