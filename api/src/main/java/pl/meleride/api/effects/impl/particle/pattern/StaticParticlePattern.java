package pl.meleride.api.effects.impl.particle.pattern;

import org.bukkit.util.Vector;

import java.util.List;

public abstract class StaticParticlePattern implements ParticlePattern {
  
  private List<Vector> points;
  
  @Override
  public List<Vector> createPattern() {
    if (this.points == null)
      this.points = this.createPatternOnce();
    
    return this.points;
  }
  
  protected abstract List<Vector> createPatternOnce();
  
}
