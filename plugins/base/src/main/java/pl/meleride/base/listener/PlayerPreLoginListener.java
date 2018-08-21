package pl.meleride.base.listener;

import java.util.Optional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import pl.meleride.api.helper.Listener;
import pl.meleride.base.MelerideBase;
import pl.meleride.base.entity.User;
import pl.meleride.base.entity.impl.UserImpl;

public class PlayerPreLoginListener implements Listener<AsyncPlayerPreLoginEvent> {

  private final MelerideBase plugin;

  public PlayerPreLoginListener(MelerideBase plugin) {
    this.plugin = plugin;
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  @Override
  public void performEvent(AsyncPlayerPreLoginEvent event) {
    Optional<User> optionalUser = this.plugin.getUserManager().getUser(event.getUniqueId());

    if (!optionalUser.isPresent()) {
      User user = new UserImpl(event.getUniqueId());
      this.plugin.getUserManager().addUser(user);
      return;
    }

    this.plugin.getUserManager().addUser(optionalUser.get());
  }

}
