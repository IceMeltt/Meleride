package pl.meleride.api.effects.impl.particle.pattern;

import org.bukkit.Location;
import pl.meleride.api.effects.impl.particle.ParticleObject;

public final class ParticlePatternPlayer {
  
  public static void play(ParticlePattern particlePattern, ParticleObject object, Location location) {
    particlePattern.createPattern().forEach((v) -> object.play(location.clone().add(v)));
  }
  
  private ParticlePatternPlayer() {
    //no instance
  }

}
