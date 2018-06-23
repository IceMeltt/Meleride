package pl.meleride.jobs.basic;

import org.bukkit.Bukkit;
import org.bukkit.event.block.BlockPlaceEvent;
import pl.meleride.jobs.JobEvent;
import pl.meleride.jobs.basic.extender.JobBasicExtender;
import pl.meleride.jobs.MelerideJobs;

public class BuilderJob extends JobBasicExtender<BlockPlaceEvent> {

  public BuilderJob(MelerideJobs plugin) {
    super(plugin);
  }

  @Override
  public String getName() {
    return "budowniczy";
  }

  @Override
  public double getMinReward() {
    return 0.10;
  }

  @Override
  public double getMaxReward() {
    return 1;
  }

  @Override
  public void onJob(BlockPlaceEvent event) {
    JobEvent jobEvent = new JobEvent(event.getPlayer(), this);
    if (jobEvent.isCancelled()) {
      return;
    }

    Bukkit.getPluginManager().callEvent(jobEvent);
  }
}