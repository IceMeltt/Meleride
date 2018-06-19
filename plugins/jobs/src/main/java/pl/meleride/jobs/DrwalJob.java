package pl.meleride.jobs;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;

public class DrwalJob extends JobExtender<BlockBreakEvent> {

  public DrwalJob(MelerideJobs plugin) {
    super(plugin);
  }

  @Override
  public String getName() {
    return "Drwal";
  }

  @Override
  public double getMinWage() {
    return 0.70;
  }

  @Override
  public double getMaxWage() {
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