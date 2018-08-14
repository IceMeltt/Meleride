package pl.meleride.api.effects.impl.particle;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import pl.meleride.api.effects.impl.AbstractPlayableEffect;

public class PreparedParticle extends AbstractPlayableEffect {
  
  private final Particle particle;
  private Vector offsets;
  private int count;
  private float extra;
  private Object data;
  
  public PreparedParticle(Particle particle) {
    this.particle = particle;
    this.offsets = new Vector();
    this.count = 1;
    this.extra = 0.0f;
  }
  
  public Particle getParticle() {
    return this.particle;
  }
  
  public Vector getOffsets() {
    return this.offsets;
  }
  
  public void setOffsets(Vector offsets) {
    this.offsets = offsets;
  }
  
  public int getCount() {
    return this.count;
  }
  
  public void setCount(int count) {
    this.count = count;
  }
  
  public float getExtra() {
    return this.extra;
  }
  
  public void setExtra(float extra) {
    this.extra = extra;
  }
  
  public Object getData() {
    return this.data;
  }
  
  public void setData(Object data) {
    this.validateData(data);
    this.data = data;
  }
  
  @Override
  public void play(Player player, Location location) {
    if (this.isDataRequired())
      this.validateData(this.data);
    
    player.spawnParticle(this.particle,
        location,
        this.count,
        this.offsets.getX(),
        this.offsets.getY(),
        this.offsets.getZ(),
        this.extra,
        this.particle.getDataType().cast(this.data));
  }
  
  @Override
  protected PreparedParticle clone() {
    PreparedParticle clone = new PreparedParticle(this.particle);
    clone.offsets = this.offsets;
    clone.count = this.count;
    clone.extra = this.extra;
    clone.data = this.data;
    return clone;
  }
  
  public boolean isDataRequired() {
    return this.particle.getDataType() != Void.class;
  }
  
  public void validateData(Object data) {
    Validate.notNull(data, "Data object cannot be null!");
    Validate.isTrue(this.particle.getDataType() != Void.class, "This particle does not require data!");
    Validate.isTrue(this.particle.getDataType().equals(data.getClass()), "Data type mismatch!");
  }
  
}
