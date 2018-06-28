package pl.meleride.jobs.event;

import org.bukkit.entity.Player;
import pl.meleride.jobs.Job;

public class JobLevelUpEvent extends JobEvent {

  public JobLevelUpEvent(Player player, Job job) {
    super(player, job);
  }

}
