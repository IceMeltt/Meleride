package pl.meleride.api.playables.player.impl;

import pl.meleride.api.playables.PlayableStatus;
import pl.meleride.api.playables.drivers.TimelineDriver;

public class LoopedTimelinePlayer extends SingleTimelinePlayer {
  
  public LoopedTimelinePlayer(TimelineDriver timelineDriver) {
    super(timelineDriver);
  }
  
  @Override
  public boolean hasFinished() {
    if (super.hasFinished())
      super.setStatus(PlayableStatus.PLAYING);
    return false;
  }
  
}
