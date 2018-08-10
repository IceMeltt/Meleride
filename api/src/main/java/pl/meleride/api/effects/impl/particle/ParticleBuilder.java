package pl.meleride.api.effects.impl.particle;

import org.bukkit.Particle;
import org.bukkit.util.Vector;
import pl.meleride.api.helper.Buildable;

public final class ParticleBuilder implements Buildable<ParticleObject> {

  private final ParticleObject particleObject;
  
  public ParticleBuilder(ParticleObject particleObject) {
    this.particleObject = particleObject;
  }
  
  public ParticleBuilder(Particle particle) {
    this.particleObject = new ParticleObject(particle);
  }
  
  public ParticleBuilder offsets(Vector offsets) {
    particleObject.setOffsets(offsets);
    return this;
  }
  
  public ParticleBuilder count(int count) {
    particleObject.setCount(count);
    return this;
  }
  
  public ParticleBuilder extra(float extra) {
    particleObject.setExtra(extra);
    return this;
  }
  
  public ParticleBuilder withData(Object data) {
    particleObject.setData(data);
    return this;
  }
  
  @Override
  public ParticleObject build() {
    if (this.particleObject.getParticle().getDataType() != Void.class)
      this.particleObject.validateData(this.particleObject.getData());
    
    return this.particleObject.clone();
  }
  
}
