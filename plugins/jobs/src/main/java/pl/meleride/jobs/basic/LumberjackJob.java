package pl.meleride.jobs.basic;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import pl.meleride.jobs.JobEvent;
import pl.meleride.jobs.basic.extender.JobBasicExtender;
import pl.meleride.jobs.MelerideJobs;

public class LumberjackJob extends JobBasicExtender<BlockBreakEvent> {

  public LumberjackJob(MelerideJobs plugin) {
    super(plugin);
  }

  @Override
  public String getName() {
    return "Drwal";
  }

  @Override
  public double getMinReward() {
    return 0.70;
  }

  @Override
  public double getMaxReward() {
    return 1;
  }

  @Override
  public void onJob(BlockBreakEvent event) {
    if (event.getBlock().getType() != Material.WOOD) {
      return;
    }

    JobEvent jobEvent = new JobEvent(event.getPlayer(), this);
    if (jobEvent.isCancelled()) {
      return;
    }

    Bukkit.getPluginManager().callEvent(jobEvent);
  }

}