package pl.meleride.api.effects.impl.effect;

import org.apache.commons.lang.Validate;
import org.bukkit.Effect;
import org.bukkit.Location;
import pl.meleride.api.effects.WorldPlayable;

public class EffectObject implements WorldPlayable, Cloneable {

  private final Effect effect;
  private Object data;
  
  public EffectObject(Effect effect) {
    this.effect = effect;
  }
  
  public Effect getEffect() {
    return effect;
  }
  
  public Object getData() {
    return data;
  }
  
  public void setData(Object data) {
    validateData(data);
    this.data = data;
  }
  
  public void validateData(Object data) {
    Validate.notNull(data, "Data object cannot be null!");
    Validate.notNull(effect.getData(), "This effect does not require data!");
    Validate.isTrue(effect.getData().equals(data), "Data type mismatch!");
  }
  
  @Override
  public void play(Location location) {
    location.getWorld().playEffect(location,
        this.effect,
        this.effect.getData().cast(this.data));
  }
  
  @Override
  public EffectObject clone() {
    EffectObject effectObject = new EffectObject(this.effect);
    effectObject.data = this.data;
    return effectObject;
  }
  
}
