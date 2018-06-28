package pl.meleride.jobs.steady.advanced;

import org.bukkit.Material;
import pl.meleride.jobs.Job;

public class ProgrammerJob implements Job {

  private double minReward;
  private double maxReward;

  @Override
  public String getName() {
    return "programista";
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
  public Material getGUIItem() {
    return Material.DIAMOND;
  }

}
