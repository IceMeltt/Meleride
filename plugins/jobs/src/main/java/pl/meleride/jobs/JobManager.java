package pl.meleride.jobs;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.apache.commons.lang.Validate;
import pl.meleride.jobs.Job.JobLevel;
import pl.meleride.jobs.Job.JobLevelImpl;

public class JobManager {

  private final Map<String, Job> jobMap = new HashMap<>();
  private final Map<UUID, JobLevel> userJobMap = new HashMap<>();

  private final JobGUI jobGUI;

  public JobManager() {
    this.jobGUI = new JobGUI(this);
  }

  public Optional<Job> getJobByName(String jobName) {
    return Optional.ofNullable(this.jobMap.get(jobName));
  }

  public void registerJob(Job job) {
    Validate.notNull(job, "Job cannot be null!");

    this.jobMap.put(job.getName(), job);
  }

  public void registerJobs(Job... jobs) {
    Arrays.stream(jobs).forEach(this::registerJob);
  }

  public ImmutableList<Job> getJobs() {
    return ImmutableList.copyOf(this.jobMap.values());
  }

  public boolean addUserJob(UUID uuid, Job job) {
    Validate.notNull(job, "Job cannot be null!");

    if (getJob(uuid).isPresent()) {
      return false;
    }

    this.userJobMap.put(uuid, new JobLevelImpl(uuid, job));
    return true;
  }

  public boolean removeUserJob(UUID uuid, Job job) {
    Validate.notNull(job, "Job cannot be null!");

    if (!getJob(uuid).isPresent()) {
      return false;
    }

    this.userJobMap.remove(uuid);
    return true;
  }

  public Optional<JobLevel> getJob(UUID uuid) {
    return Optional.ofNullable(this.userJobMap.get(uuid));
  }

  public JobGUI getJobGUI() {
    return jobGUI;
  }

}