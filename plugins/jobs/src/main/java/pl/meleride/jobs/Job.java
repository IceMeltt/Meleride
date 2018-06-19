package pl.meleride.jobs;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public interface Job<T extends Event> extends Listener {

  String getName();

  double getMinWage();

  double getMaxWage();

  @EventHandler
  void onJob(T event);

}
