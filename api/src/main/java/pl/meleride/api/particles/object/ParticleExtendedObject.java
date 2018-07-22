package pl.meleride.api.particles.object;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public final class ParticleExtendedObject extends ParticleObject {

  private Object particleData;

  protected ParticleExtendedObject(Particle particle) {
    super(particle);
    Validate.isTrue(particle.getDataType() != Void.class, "This particle requires simple object!");
  }

  @Override
  public void spawn(Location location) {
    Validate.notNull(particleData, "Particle data has not been set!");
    location.getWorld().spawnParticle(super.getParticle(),
        location,
        super.count,
        super.offsets.getX(),
        super.offsets.getY(),
        super.offsets.getZ(),
        super.speed,
        super.getParticle().getDataType().cast(this.particleData));
  }

  @Override
  public void spawn(Player target, Location location) {
    Validate.notNull(particleData, "Particle data has not been set!");
    target.spawnParticle(super.getParticle(),
        location,
        super.count,
        super.offsets.getX(),
        super.offsets.getY(),
        super.offsets.getZ(),
        super.speed,
        super.getParticle().getDataType().cast(this.particleData));
  }

  public ParticleExtendedObject setOffsets(Vector offsets) {
    super.offsets = offsets;
    return this;
  }

  public ParticleExtendedObject setOffsets(float x, float y, float z) {
    super.offsets = new Vector(x, y, z);
    return this;
  }

  public ParticleExtendedObject setCount(int count) {
    Validate.isTrue(count >= 0, "Count must be equals or greater than 0!");
    super.count = count;
    return this;
  }

  public ParticleExtendedObject setSpeed(float speed) {
    Validate.isTrue(speed >= 0, "Speed must be equals or greater than 0!");
    super.speed = speed;
    return this;
  }

  public ParticleExtendedObject setData(Object data) {
    Validate.notNull(data, "Data cannot be null!");
    Validate.isTrue(super.getParticle().getDataType().equals(data.getClass()), "Required - Given data type mismatch!");
    this.particleData = data;
    return this;
  }

}
