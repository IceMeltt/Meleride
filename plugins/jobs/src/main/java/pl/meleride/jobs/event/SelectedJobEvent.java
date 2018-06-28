package pl.meleride.jobs.event;

import org.bukkit.entity.Player;
import pl.meleride.jobs.Job;

public class SelectedJobEvent extends JobEvent {

  public SelectedJobEvent(Player player, Job job) {
    super(player, job);
  }

}
