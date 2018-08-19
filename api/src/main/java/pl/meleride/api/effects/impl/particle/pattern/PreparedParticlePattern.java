package pl.meleride.api.effects.impl.particle.pattern;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import pl.meleride.api.effects.impl.particle.PreparedParticle;

public class PreparedParticlePattern extends PreparedParticle {
  
  private final ParticlePattern particlePattern;
  
  public PreparedParticlePattern(Particle particle, ParticlePattern particlePattern) {
    super(particle);
    this.particlePattern = particlePattern;
  }
  
  public PreparedParticlePattern(PreparedParticle preparedParticle, ParticlePattern pattern) {
    super(preparedParticle.getParticle());
    super.setOffsets(preparedParticle.getOffsets());
    super.setCount(preparedParticle.getCount());
    super.setExtra(preparedParticle.getExtra());
    
    if (preparedParticle.getData() != null)
      super.setData(preparedParticle.getData());
    
    this.particlePattern = pattern;
  }
  
  @Override
  public void play(Player player, Location location) {
    this.particlePattern.createPattern().forEach((v) -> super.play(player, location.clone().add(v)));
  }
  
}
