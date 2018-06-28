package pl.meleride.jobs.steady.basic.extender;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.meleride.jobs.Job;
import pl.meleride.jobs.MelerideJobs;

public abstract class BasicJobExtender<T extends Event> implements Job, Listener {

  public BasicJobExtender(MelerideJobs plugin) {
    Bukkit.getPluginManager().registerEvents(this, plugin);
  }

  @EventHandler(ignoreCancelled = true)
  public abstract void onJob(T event);

  @Override
  public Material getGUIItem() {
    return Material.STONE;
  }

}
