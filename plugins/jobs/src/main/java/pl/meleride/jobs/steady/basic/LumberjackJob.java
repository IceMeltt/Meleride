package pl.meleride.jobs.steady.basic;

import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import pl.meleride.jobs.MelerideJobs;
import pl.meleride.jobs.steady.basic.extender.BasicJobExtender;

public class LumberjackJob extends BasicJobExtender<BlockBreakEvent> {

  private double minReward;
  private double maxReward;

  public LumberjackJob(MelerideJobs plugin) {
    super(plugin);
  }

  @Override
  public String getName() {
    return "Drwal";
  }

  @Override
  public double getMinReward() {
    return minReward;
  }

  @Override
  public void setMinReward(double minReward) {
    this.minReward = minReward;
  }

  @Override
  public double getMaxReward() {
    return maxReward;
  }

  @Override
  public void setMaxReward(double maxReward) {
    this.maxReward = maxReward;
  }

  @Override
  public void onJob(BlockBreakEvent event) {
    if (event.getBlock().getType() != Material.WOOD) {
      return;
    }

    callEvent(event.getPlayer());
  }

}