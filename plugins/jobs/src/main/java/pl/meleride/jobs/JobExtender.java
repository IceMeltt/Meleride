package pl.meleride.jobs;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

public abstract class JobExtender<T extends Event> implements Job<T> {

  public JobExtender(MelerideJobs plugin) {
    Bukkit.getPluginManager().registerEvents(this, plugin);
  }

}
