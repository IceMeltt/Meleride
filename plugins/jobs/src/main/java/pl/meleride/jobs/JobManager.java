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

public class JobManager {

  private final Map<String, Job> jobMap = new HashMap<>();
  private final Multimap<UUID, Job> userJobMap = HashMultimap.create();

  private static final int MAX_USER_JOBS = 2;

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

  public ImmutableList<Job> getUserJobs(UUID uuid) {
    return ImmutableList.copyOf(this.userJobMap.get(uuid));
  }

  public boolean addUserJob(UUID uuid, Job job) {
    Validate.notNull(job, "Job cannot be null!");

    if (this.getUserJobs(uuid).size() == MAX_USER_JOBS) {
      return false;
    }

    return this.userJobMap.put(uuid, job);
  }

  public JobGUI getJobGUI() {
    return jobGUI;
  }
}
