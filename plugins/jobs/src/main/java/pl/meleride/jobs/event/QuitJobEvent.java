package pl.meleride.jobs.event;

import org.bukkit.entity.Player;
import pl.meleride.jobs.Job;

public class QuitJobEvent extends JobEvent {

  public QuitJobEvent(Player player, Job job) {
    super(player, job);
  }

}
