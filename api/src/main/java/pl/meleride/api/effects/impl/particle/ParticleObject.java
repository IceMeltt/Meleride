package pl.meleride.api.effects.impl.particle;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import pl.meleride.api.effects.PlayerPlayable;
import pl.meleride.api.effects.WorldPlayable;

public class ParticleObject implements WorldPlayable, PlayerPlayable, Cloneable {
  
  private final Particle particle;
  private Vector offsets;
  private int count;
  private float extra;
  private Object data;
  
  public ParticleObject(Particle particle) {
    this.particle = particle;
    this.offsets = new Vector();
    this.count = 1;
    this.extra = 0;
  }
  
  public Particle getParticle() {
    return particle;
  }
  
  public Vector getOffsets() {
    return offsets;
  }
  
  public void setOffsets(Vector offsets) {
    this.offsets = offsets;
  }
  
  public int getCount() {
    return count;
  }
  
  public void setCount(int count) {
    this.count = count;
  }
  
  public float getExtra() {
    return extra;
  }
  
  public void setExtra(float extra) {
    this.extra = extra;
  }
  
  public Object getData() {
    return data;
  }
  
  public void setData(Object data) {
    validateData(data);
    this.data = data;
  }
  
  @Override
  public void play(Location location) {
    if (this.particle.getDataType() != Void.class)
      validateData(this.data);
    
    location.getWorld().spawnParticle(particle,
        location,
        this.count,
        this.offsets.getX(),
        this.offsets.getY(),
        this.offsets.getZ(),
        this.extra,
        particle.getDataType().cast(this.data));
  }
  
  @Override
  public void play(Player player, Location location) {
    if (this.particle.getDataType() != Void.class)
      validateData(this.data);
  
    player.spawnParticle(particle,
        location,
        this.count,
        this.offsets.getX(),
        this.offsets.getY(),
        this.offsets.getZ(),
        this.extra,
        particle.getDataType().cast(this.data));
  }
  
  @Override
  protected ParticleObject clone() {
    ParticleObject particleObject = new ParticleObject(this.particle);
    particleObject.offsets = this.offsets;
    particleObject.count = this.count;
    particleObject.extra = this.extra;
    particleObject.data = this.data;
    return particleObject;
  }
  
  public void validateData(Object data) {
    Validate.notNull(data, "Data object cannot be null!");
    Validate.isTrue(this.particle.getDataType() != Void.class, "This particle does not require data!");
    Validate.isTrue(this.particle.getDataType() == this.data.getClass(), "Data type mismatch!");
  }
  
}
