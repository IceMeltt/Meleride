package pl.meleride.api.playables.drivers;

import pl.meleride.api.playables.TimeUnit;

public interface TimelineDriver {
  
  TimeUnit getTimelineLength();
  
  void handleEvent(TimeUnit time);
  
}
