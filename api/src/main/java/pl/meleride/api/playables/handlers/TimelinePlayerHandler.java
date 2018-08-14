package pl.meleride.api.playables.handlers;

import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.api.playables.player.TimelinePlayer;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class TimelinePlayerHandler<T extends Serializable> implements Runnable {
  
  private final Map<T, TimelinePlayer> playerMap;
  
  public TimelinePlayerHandler(JavaPlugin plugin) {
    this.playerMap = new ConcurrentHashMap<>();
    
    plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this, 0, 1);
  }
  
  public void put(T key, TimelinePlayer player) {
    this.playerMap.put(key, player);
  }
  
  public TimelinePlayer get(T key) {
    return this.playerMap.get(key);
  }
  
  public void remove(T key) {
    this.playerMap.remove(key);
  }
  
  @Override
  public void run() {
    playerMap.entrySet().removeIf((e) -> {
      e.getValue().update();
      return e.getValue().hasFinished();
    });
  }
  
}
