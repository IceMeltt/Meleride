package pl.meleride.api.particles.object;

import org.bukkit.Particle;

public class ParticleBundler {

  public static ParticleSimpleObject createSimple(Particle particle) {
    return new ParticleSimpleObject(particle);
  }

  public static ParticleExtendedObject createExtended(Particle particle) {
    return new ParticleExtendedObject(particle);
  }

  private ParticleBundler() {
  }

}
