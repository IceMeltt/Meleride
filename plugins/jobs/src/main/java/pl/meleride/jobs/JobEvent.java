package pl.meleride.jobs;

import org.bukkit.entity.Player;
import pl.meleride.api.event.CustomEvent;

public class JobEvent extends CustomEvent {

  private final Player player;
  private final Job job;

  public JobEvent(Player player, Job job) {
    this.player = player;
    this.job = job;
  }

  public Player getPlayer() {
    return player;
  }

  public Job getJob() {
    return job;
  }

}