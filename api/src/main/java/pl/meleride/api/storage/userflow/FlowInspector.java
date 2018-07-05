package pl.meleride.api.storage.userflow;

import org.bukkit.entity.Player;
import pl.meleride.api.flexible.PlayableUser;

public interface FlowInspector<T extends PlayableUser> {

  void join(Player player);

  void quit(Player player);

  T getUser();

}
