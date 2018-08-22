package pl.meleride.api.effects.impl.effect;

import org.apache.commons.lang.Validate;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.meleride.api.effects.impl.AbstractPlayableEffect;

public class PreparedEffect extends AbstractPlayableEffect {
  
  private final Effect effect;
  private Object data;
  
  public PreparedEffect(Effect effect) {
    this.effect = effect;
  }
  
  public Effect getEffect() {
    return this.effect;
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
    
    player.playEffect(location,
        this.effect,
        this.isDataRequired() ? this.effect.getData().cast(this.data) : null);
  }
  
  @Override
  public PreparedEffect clone() {
    PreparedEffect clone = new PreparedEffect(this.effect);
    clone.data = this.data;
    return clone;
  }
  
  public boolean isDataRequired() {
    return this.effect.getData() != null;
  }
  
  public void validateData(Object data) {
    Validate.notNull(data, "Data object cannot be null!");
    Validate.notNull(this.effect.getData(), "This effect does not require data!");
    Validate.isTrue(this.effect.getData().equals(data.getClass()), "Data type mismatch!");
  }
  
}
