package pl.meleride.api.user.caller;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import pl.meleride.api.user.User;
import pl.meleride.api.user.event.UserAbortEvent;
import pl.meleride.api.user.event.UserInitEvent;
import pl.meleride.api.user.manager.UserManager;

import javax.inject.Inject;

public class PlayerPreLoginListener implements Listener {

  @Inject
  private UserManager userManager;

  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
    User user = this.userManager.getUser(event.getUniqueId()).get();

    UserInitEvent userInitEvent = new UserInitEvent(user);
    Bukkit.getPluginManager().callEvent(userInitEvent);

    if (userInitEvent.isCancelled()) {
      UserAbortEvent userAbortEvent = new UserAbortEvent(user);
      Bukkit.getPluginManager().callEvent(userAbortEvent);
      event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "Session abort.");
      return;
    }

    this.userManager.addUser(user);
  }

}
