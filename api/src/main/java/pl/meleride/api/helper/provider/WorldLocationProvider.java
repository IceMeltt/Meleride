package pl.meleride.api.helper.provider;

import org.bukkit.Location;
import pl.meleride.api.helper.Provider;

public class WorldLocationProvider implements Provider<Location> {

  private final Location location;

  public WorldLocationProvider(Location location) {
    this.location = location;
  }

  @Override
  public boolean isAvailable() {
    return true;
  }

  @Override
  public Location get() {
    return this.location;
  }
}
