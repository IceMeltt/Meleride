package pl.meleride.api.effects.impl.effect;

import org.bukkit.Effect;
import pl.meleride.api.helper.Buildable;

public final class EffectBuilder implements Buildable<EffectObject> {
  
  private final EffectObject effectObject;
  
  public EffectBuilder(Effect effect) {
    this.effectObject = new EffectObject(effect);
  }
  
  public EffectBuilder(EffectObject effectObject) {
    this.effectObject = effectObject;
  }
  
  public void data(Object data) {
    effectObject.setData(data);
  }
  
  @Override
  public EffectObject build() {
    return this.effectObject.clone();
  }
  
}
