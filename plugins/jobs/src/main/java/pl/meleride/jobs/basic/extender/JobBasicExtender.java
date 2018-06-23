package pl.meleride.jobs.basic.extender;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.meleride.jobs.Job;
import pl.meleride.jobs.MelerideJobs;

public abstract class JobBasicExtender<T extends Event> implements Job, Listener {

  public JobBasicExtender(MelerideJobs plugin) {
    Bukkit.getPluginManager().registerEvents(this, plugin);
  }

  @EventHandler
  public abstract void onJob(T event);

  @Override
  public Material getGUIItem() {
    return Material.DIAMOND;
  }
}
