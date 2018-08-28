package pl.meleride.api.playables.player.impl;

import pl.meleride.api.playables.PlayableStatus;
import pl.meleride.api.playables.TimeUnit;
import pl.meleride.api.playables.drivers.TimelineDriver;
import pl.meleride.api.playables.player.TimelinePlayer;

public class SingleTimelinePlayer implements TimelinePlayer {
  
  private static final TimeUnit incrementValue = TimeUnit.ticks(1);
  
  private final TimelineDriver timelineDriver;
  private TimeUnit currentTime;
  private PlayableStatus status;
  private boolean hasPlayed;
  
  public SingleTimelinePlayer(TimelineDriver timelineDriver) {
    this.timelineDriver = timelineDriver;
    this.currentTime = TimeUnit.zero();
  }
  
  public TimelineDriver getTimelineDriver() {
    return timelineDriver;
  }
  
  public TimeUnit getCurrentTime() {
    return currentTime;
  }
  
  @Override
  public void update() {
    if (this.status != PlayableStatus.PLAYING)
      return;
    
    this.timelineDriver.handleEvent(this.currentTime);
    if (this.currentTime.equals(this.timelineDriver.getTimelineLength())) {
      this.setStatus(PlayableStatus.STOPPED);
      this.hasPlayed = true;
    }
    
    this.currentTime.add(incrementValue);
  }
  
  @Override
  public void setStatus(PlayableStatus status) {
    switch (status) {
      case PLAYING:
        if (this.status == PlayableStatus.STOPPED)
          this.currentTime = TimeUnit.zero();
      case STOPPED:
      case PAUSED:
        this.status = status;
    }
  }
  
  @Override
  public PlayableStatus getStatus() {
    return this.status;
  }
  
  @Override
  public boolean hasFinished() {
    return this.hasPlayed;
  }
  
}
