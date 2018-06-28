package pl.meleride.jobs.steady.basic;

import org.bukkit.event.block.BlockPlaceEvent;
import pl.meleride.jobs.MelerideJobs;
import pl.meleride.jobs.steady.basic.extender.BasicJobExtender;

public class BuilderJob extends BasicJobExtender<BlockPlaceEvent> {

  private double minReward;
  private double maxReward;

  public BuilderJob(MelerideJobs plugin) {
    super(plugin);
  }

  @Override
  public String getName() {
    return "budowniczy";
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
  public void onJob(BlockPlaceEvent event) {
    callEvent(event.getPlayer());
  }

}