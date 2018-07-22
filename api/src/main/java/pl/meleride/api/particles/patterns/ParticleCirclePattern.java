package pl.meleride.api.particles.patterns;

import org.apache.commons.lang.Validate;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ParticleCirclePattern implements ParticlePattern {

  private final float radius;
  private final float particlesCount;

  public ParticleCirclePattern(float radius, float particlesCount) {
    Validate.isTrue(radius > 0, "Radius must be greater than 0!");
    Validate.isTrue(particlesCount >= 4, "Circle requires at least 4 particles!");
    this.radius = radius;
    this.particlesCount = particlesCount;
  }

  @Override
  public List<Vector> create() {
    ArrayList<Vector> points = new ArrayList<>();

    double angle;
    for (int i = 0; i < particlesCount; i++) {
      angle = 2 * Math.PI * i / particlesCount;
      points.add(new Vector(Math.cos(angle) * radius, 0, Math.sin(angle) * radius));
    }

    return points;
  }

}
