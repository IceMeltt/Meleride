package pl.meleride.jobs;

import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class JobListener implements Listener {

  @EventHandler(ignoreCancelled = true)
  public void onJob(final JobEvent event) {
    Job job = event.getJob();
    double min = job.getMinWage();
    double max = job.getMaxWage();

                        //dopoki nie zostanie wprowadzony randomutil
    double randomWage = ThreadLocalRandom.current().nextDouble(min, max);
    //przelac pieniadze na konto gracza
  }

}
