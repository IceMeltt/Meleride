package pl.meleride.api.effects.impl.effect;

import org.bukkit.Effect;
import pl.meleride.api.helper.Buildable;

public final class EffectBuilder implements Buildable<PreparedEffect> {
  
  private final PreparedEffect preparedEffect;
  
  public EffectBuilder(Effect effect) {
    this.preparedEffect = new PreparedEffect(effect);
  }
  
  public EffectBuilder(PreparedEffect preparedEffect) {
    this.preparedEffect = preparedEffect;
  }
  
  public EffectBuilder setData(Object data) {
    this.preparedEffect.setData(data);
    return this;
  }
  
  @Override
  public PreparedEffect build() {
    if (this.preparedEffect.isDataRequired())
      this.preparedEffect.validateData(this.preparedEffect.getData());
    return this.preparedEffect.clone();
  }
  
}
