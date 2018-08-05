package pl.meleride.api.hologram;

import de.inventivegames.hologram.Hologram;
import de.inventivegames.hologram.HologramAPI;
import pl.meleride.api.helper.Buildable;
import pl.meleride.api.message.MessageUtil;

public class HologramBuilder implements Buildable<Hologram> {

  private HologramOptions hologramOptions;

  public HologramBuilder(final HologramOptions hologramOptions) {
    this.hologramOptions = hologramOptions;
  }

  public void setHologramOptions(final HologramOptions hologramOptions) {
    this.hologramOptions = hologramOptions;
  }

  @Override
  public Hologram build() {
    final Hologram hologram = HologramAPI
        .createHologram(this.hologramOptions.getLocation(), this.hologramOptions.getTitle());
    MessageUtil.colored(this.hologramOptions.getLines())
        .forEach(hologram::addLineBelow);
    return hologram;
  }

}
