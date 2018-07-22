package pl.meleride.api.particles.object;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import pl.meleride.api.particles.patterns.ParticlePattern;

import java.util.Collection;

public abstract class ParticleObject {

  private final Particle particle;

  protected Vector offsets;
  protected int count;
  protected float speed;

  protected ParticleObject(Particle particle) {
    this.particle = particle;

    this.offsets = new Vector(0, 0, 0);
    this.count = 0;
    this.speed = 0.0f;
  }

  public Particle getParticle() {
    return particle;
  }

  public Vector getOffsets() {
    return offsets;
  }

  public int getCount() {
    return count;
  }

  public float getSpeed() {
    return speed;
  }

  public abstract void spawn(Location location);

  public abstract void spawn(Player target, Location location);

  public void spawn(Collection<? extends Player> players, Location location) {
    players.forEach(player -> this.spawn(player, location));
  }

  public void spawnPattern(Location location, ParticlePattern pattern) {
    pattern.create().forEach(offset -> this.spawn(location.clone().add(offset)));
  }

  public void spawnPattern(Player player, Location location, ParticlePattern pattern) {
    pattern.create().forEach(offset -> this.spawn(player, location.clone().add(offset)));
  }

  public void spawnPattern(Collection<? extends Player> players, Location location, ParticlePattern pattern) {
    players.forEach(player -> this.spawnPattern(player, location, pattern));
  }


}
