package pl.meleride.jobs;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.meleride.api.math.RandomUtil;

public class JobListener implements Listener {

  @EventHandler(ignoreCancelled = true)
  public void onJob(final JobEvent event) {
    Job job = event.getJob();
    double min = job.getMinReward();
    double max = job.getMaxReward();

    double reward = RandomUtil.nextDouble(min, max);
    //przelac pieniadze na konto gracza
  }

}
