package pl.meleride.api.effects.impl.particle;

import org.bukkit.Particle;
import org.bukkit.util.Vector;
import pl.meleride.api.effects.impl.particle.pattern.ParticlePattern;
import pl.meleride.api.effects.impl.particle.pattern.PreparedParticlePattern;
import pl.meleride.api.helper.Buildable;

public final class ParticleBuilder implements Buildable<PreparedParticle> {
  
  private final PreparedParticle preparedParticle;
  
  public ParticleBuilder(Particle particle) {
    this.preparedParticle = new PreparedParticle(particle);
  }
  
  public ParticleBuilder(PreparedParticle preparedParticle) {
    this.preparedParticle = preparedParticle;
  }
  
  public ParticleBuilder setOffsets(Vector offsets) {
    this.preparedParticle.setOffsets(offsets);
    return this;
  }
  
  public ParticleBuilder setCount(int count) {
    this.preparedParticle.setCount(count);
    return this;
  }
  
  public ParticleBuilder setExtra(float extra) {
    this.preparedParticle.setExtra(extra);
    return this;
  }
  
  public ParticleBuilder setData(Object data) {
    this.preparedParticle.setData(data);
    return this;
  }
  
  public PreparedParticlePattern pattern(ParticlePattern pattern) {
    return new PreparedParticlePattern(this.build(), pattern);
  }
  
  @Override
  public PreparedParticle build() {
    if (this.preparedParticle.isDataRequired())
      this.preparedParticle.validateData(this.preparedParticle.getData());
    return this.preparedParticle.clone();
  }
  
}
