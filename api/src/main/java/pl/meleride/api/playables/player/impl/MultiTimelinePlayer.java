package pl.meleride.api.playables.player.impl;

import pl.meleride.api.playables.drivers.TimelineDriver;

public class MultiTimelinePlayer extends SingleTimelinePlayer {
  
  public MultiTimelinePlayer(TimelineDriver timelineDriver) {
    super(timelineDriver);
  }
  
  @Override
  public boolean hasFinished() {
    return false;
  }
  
}
