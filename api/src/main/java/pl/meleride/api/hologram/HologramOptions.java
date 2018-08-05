package pl.meleride.api.hologram;

import com.google.common.collect.ImmutableList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import pl.meleride.api.helper.LocationHelper;

public class HologramOptions {

  private final String title;
  private final List<String> lines;

  private final Location location;

  public HologramOptions(final String title, final List<String> lines, final Location location) {
    this.title = title;
    this.lines = lines;
    this.location = location;
  }

  public HologramOptions(final ConfigurationSection section) {
    this.title = section.getString("title");
    this.lines = section.getStringList("lines");
    this.location = LocationHelper.fromSection(section.getConfigurationSection("location"));
  }

  public String getTitle() {
    return title;
  }

  public Location getLocation() {
    return location;
  }

  public ImmutableList<String> getLines() {
    return ImmutableList.copyOf(lines);
  }

}
