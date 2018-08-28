package pl.meleride.api.playables.player;

import pl.meleride.api.helper.StatusChangeable;
import pl.meleride.api.helper.Updateable;
import pl.meleride.api.playables.PlayableStatus;

public interface TimelinePlayer extends StatusChangeable<PlayableStatus>, Updateable {
  
  boolean hasFinished();
  
}
