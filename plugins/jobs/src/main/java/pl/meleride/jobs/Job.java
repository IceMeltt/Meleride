package pl.meleride.jobs;

import org.bukkit.Material;

public interface Job {

  String getName();

  double getMinReward();

  double getMaxReward();

  Material getGUIItem();

}
