package pl.meleride.jobs;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;

public class MinerJob extends JobExtender<BlockBreakEvent> {

  public MinerJob(MelerideJobs plugin) {
    super(plugin);
  }

  @Override
  public String getName() {
    return "Gornik";
  }

  @Override
  public double getMinReward() {
    return 0.20;
  }

  @Override
  public double getMaxReward() {
    return 1.30;
  }

  @Override
  public void onJob(BlockBreakEvent event) {
    if (event.getBlock().getType() != Material.STONE) {
      return;
    }

    JobEvent jobEvent = new JobEvent(event.getPlayer(), this);
    if (jobEvent.isCancelled()) {
      return;
    }

    Bukkit.getPluginManager().callEvent(jobEvent);
  }


}
