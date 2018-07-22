package pl.meleride.api.particles.object;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;

public final class ParticleSimpleObject extends ParticleObject {

  @Deprecated
  private static final List<Particle> colorizedParticles =
      Arrays.asList(Particle.REDSTONE, Particle.SPELL_MOB, Particle.SPELL_MOB_AMBIENT);

  protected ParticleSimpleObject(Particle particle) {
    super(particle);
    Validate.isTrue(particle.getDataType() == Void.class, "This particle requires extended object!");
  }

  @Override
  public void spawn(Location location) {
    location.getWorld().spawnParticle(super.getParticle(),
        location,
        super.count,
        super.offsets.getX(),
        super.offsets.getY(),
        super.offsets.getZ(),
        super.speed);
  }

  @Override
  public void spawn(Player target, Location location) {
    target.getWorld().spawnParticle(super.getParticle(),
        location,
        super.count,
        super.offsets.getX(),
        super.offsets.getY(),
        super.offsets.getZ(),
        super.speed);
  }

  public ParticleSimpleObject setOffsets(Vector offsets) {
    super.offsets = offsets;
    return this;
  }

  public ParticleSimpleObject setOffsets(float x, float y, float z) {
    super.offsets = new Vector(x, y, z);
    return this;
  }

  public ParticleSimpleObject setCount(int count) {
    Validate.isTrue(count >= 0, "Count must be equals or greater than 0!");
    super.count = count;
    return this;
  }

  public ParticleSimpleObject setSpeed(float speed) {
    Validate.isTrue(speed >= 0, "Speed must be equals or greater than 0!");
    super.speed = speed;
    return this;
  }

  @Deprecated
  public ParticleSimpleObject configureColorized() {
    Validate.isTrue(colorizedParticles.contains(this.getParticle()),"This particle cannot be colorized");
    super.speed = 1;
    super.count = 0;
    return this;
  }

  @Deprecated
  public ParticleSimpleObject setColor(int red, int green, int blue) {
    Validate.isTrue(colorizedParticles.contains(this.getParticle()),"This particle cannot be colorized");
    super.offsets = new Vector( (red + 1.0f) / 255.0f, green / 255.0f, blue / 255.0f);
    return this;
  }

}
