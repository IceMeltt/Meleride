package pl.meleride.api.effects;

import org.bukkit.Effect;
import org.bukkit.Particle;
import org.bukkit.Sound;
import pl.meleride.api.effects.impl.effect.EffectBuilder;
import pl.meleride.api.effects.impl.effect.PreparedEffect;
import pl.meleride.api.effects.impl.particle.ParticleBuilder;
import pl.meleride.api.effects.impl.particle.PreparedParticle;
import pl.meleride.api.effects.impl.sound.PreparedSound;
import pl.meleride.api.effects.impl.sound.SoundBuilder;

public final class EffectBundler {
  
  public static ParticleBuilder create(Particle particle) {
    return new ParticleBuilder(particle);
  }
  
  public static ParticleBuilder edit(PreparedParticle particle) {
    return new ParticleBuilder(particle);
  }
  
  public static SoundBuilder create(Sound sound) {
    return new SoundBuilder(sound);
  }
  
  public static SoundBuilder edit(PreparedSound sound) {
    return new SoundBuilder(sound);
  }
  
  public static EffectBuilder create(Effect effect) {
    return new EffectBuilder(effect);
  }
  
  public static EffectBuilder edit(PreparedEffect effect) {
    return new EffectBuilder(effect);
  }
  
  private EffectBundler() {
    //no instance
  }
  
}
