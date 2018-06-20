package pl.meleride.jobs;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public interface Job<T extends Event> extends Listener {

  String getName();

  double getMinReward();

  double getMaxReward();

  @EventHandler
  void onJob(T event);

}
