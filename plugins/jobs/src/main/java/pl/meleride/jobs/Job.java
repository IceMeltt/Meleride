package pl.meleride.jobs;

import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.meleride.jobs.event.JobEvent;

public interface Job {

  String getName();

  double getMinReward();

  void setMinReward(double minReward);

  double getMaxReward();

  void setMaxReward(double maxReward);

  Material getGUIItem();

  default void callEvent(Player player) {
    JobEvent jobEvent = new JobEvent(player, this);

    if (jobEvent.isCancelled()) {
      return;
    }

    Bukkit.getPluginManager().callEvent(jobEvent);
  }

  interface JobLevel {

    UUID getUniqueId();

    Job getJob();

    int getJobLevel();

    void setJobLevel(int jobLevel);

    void jobLevelUp();

    int getJobExp();

    void addJobExp(int exp);

  }

  class JobLevelImpl implements JobLevel {

    private final UUID uuid;
    private final Job job;

    private int jobLevel;
    private int jobExp;

    JobLevelImpl(UUID uuid, Job job) {
      this.uuid = uuid;
      this.job = job;
    }

    @Override
    public UUID getUniqueId() {
      return uuid;
    }

    @Override
    public Job getJob() {
      return job;
    }

    @Override
    public int getJobLevel() {
      return jobLevel;
    }

    @Override
    public void setJobLevel(int jobLevel) {
      this.jobLevel = jobLevel;
    }

    @Override
    public void jobLevelUp() {
      this.jobLevel += 1;
    }

    @Override
    public int getJobExp() {
      return jobExp;
    }

    @Override
    public void addJobExp(int exp) {
      this.jobExp += exp;
    }

  }

}