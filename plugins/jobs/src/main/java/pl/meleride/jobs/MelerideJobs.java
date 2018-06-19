package pl.meleride.jobs;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class MelerideJobs extends JavaPlugin {

  private JobManager jobManager;

  @Override
  public void onEnable() {
    this.jobManager = new JobManager();

    this.jobManager.registerJobs(
        new DrwalJob(this),
        new MinerJob(this)
    );

    Bukkit.getPluginManager().registerEvents(new JobListener(), this);
  }

  public JobManager getJobManager() {
    Validate.notNull(jobManager);

    return jobManager;
  }
}
