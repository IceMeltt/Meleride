package pl.meleride.api.effects;

import org.bukkit.Effect;
import org.bukkit.Particle;
import org.bukkit.Sound;
import pl.newdimension.api.effects.impl.effect.EffectBuilder;
import pl.newdimension.api.effects.impl.effect.EffectObject;
import pl.newdimension.api.effects.impl.particle.ParticleBuilder;
import pl.newdimension.api.effects.impl.particle.ParticleObject;
import pl.newdimension.api.effects.impl.sound.SoundBuilder;
import pl.newdimension.api.effects.impl.sound.SoundObject;

public final class EffectBundler {
  
  public static ParticleBuilder create(Particle particle) {
    return new ParticleBuilder(particle);
  }
  
  public static ParticleBuilder edit(ParticleObject particleObject) {
    return new ParticleBuilder(particleObject);
  }
  
  public static SoundBuilder create(Sound sound) {
    return new SoundBuilder(sound);
  }
  
  public static SoundBuilder edit(SoundObject soundObject) {
    return new SoundBuilder(soundObject);
  }
  
  public static EffectBuilder create(Effect effect) {
    return new EffectBuilder(effect);
  }
  
  public static EffectBuilder edit(EffectObject object) {
    return new EffectBuilder(object);
  }
  
  private EffectBundler() {
    //No instance
  }
  
}
