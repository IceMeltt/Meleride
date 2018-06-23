package pl.meleride.jobs.advanced;

import org.bukkit.Material;
import pl.meleride.jobs.Job;

public class ProgrammerJob implements Job {

  @Override
  public String getName() {
    return "programista";
  }

  @Override
  public double getMinReward() {
    return 999999998;
  }

  @Override
  public double getMaxReward() {
    return 999999999;
  }

  @Override
  public Material getGUIItem() {
    return Material.DIAMOND;
  }
}
