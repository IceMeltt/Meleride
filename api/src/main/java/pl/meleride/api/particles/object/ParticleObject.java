package pl.meleride.api.particles.object;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

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



}
