package pl.meleride.api.user.caller;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import pl.meleride.api.user.User;
import pl.meleride.api.user.UserImpl;
import pl.meleride.api.user.event.UserAbortEvent;
import pl.meleride.api.user.event.UserInitEvent;
import pl.meleride.api.user.manager.UserManager;

import javax.inject.Inject;
import java.util.Optional;

import static pl.meleride.api.message.MessageUtil.*;

public class PlayerPreLoginListener implements Listener {

  private final UserManager userManager;

  @Inject
  PlayerPreLoginListener(UserManager userManager) {
    this.userManager = userManager;
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
    Optional<User> optionalUser = this.userManager.getUser(event.getUniqueId());

    optionalUser.ifPresent(user -> {
      UserInitEvent userInitEvent = new UserInitEvent(user);

      if (userInitEvent.isCancelled()) {
        UserAbortEvent userAbortEvent = new UserAbortEvent(user);
        Bukkit.getPluginManager().callEvent(userAbortEvent);
        event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "Session aborted.");
        return;
      }

      this.userManager.addUser(user);
    });

    if (!optionalUser.isPresent()) {
      User user = new UserImpl(event.getUniqueId());
      this.userManager.addUser(user);
    }
  }

}
