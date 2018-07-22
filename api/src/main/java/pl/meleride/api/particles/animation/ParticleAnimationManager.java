package pl.meleride.api.particles.animation;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ParticleAnimationManager<T> {

  private final Map<T, ParticleAnimation> animationMap = new HashMap<>();

  public ParticleAnimationManager(JavaPlugin plugin) {
    plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this::updateAnimations, 1,1);
  }

  public synchronized void playAnimation(T key, ParticleAnimation animation) {
    this.animationMap.putIfAbsent(key, animation);
  }

  public synchronized void stopAnimation(T key) {
    if (this.animationMap.containsKey(key))
      this.animationMap.get(key).stop();
  }

  private synchronized void updateAnimations() {
    Iterator<Map.Entry<T, ParticleAnimation>> iterator = this.animationMap.entrySet().iterator();
    while (iterator.hasNext()) {
      Map.Entry<T, ParticleAnimation> entry = iterator.next();

      if (!entry.getValue().isRunning())
        iterator.remove();
      else
        entry.getValue().update();
    }
  }

}
