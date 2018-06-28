package pl.meleride.jobs;

import java.util.Optional;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.meleride.api.util.RandomUtil;
import pl.meleride.jobs.Job.JobLevel;
import pl.meleride.jobs.event.JobEvent;

public class JobListener implements Listener {

  private final MelerideJobs plugin;

  JobListener(MelerideJobs plugin) {
    this.plugin = plugin;
  }

  @EventHandler(ignoreCancelled = true)
  public void onJob(final JobEvent event) {
    Player player = event.getPlayer();
    Job job = event.getJob();
    JobManager manager = this.plugin.getJobManager();

    Optional<JobLevel> playerJob = manager.getJob(player.getUniqueId());

    if (!playerJob.isPresent() || !playerJob.get().getJob().equals(job)) {
      return;
    }

    playerJob.get().addJobExp(RandomUtil.nextInt(2, 5));

    double min = job.getMinReward();
    double max = job.getMaxReward();

    double reward = RandomUtil.nextDouble(min, max);
    //przelac pieniadze na konto gracza
  }

}