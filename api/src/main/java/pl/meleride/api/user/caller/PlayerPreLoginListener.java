package pl.meleride.api.user.caller;

import java.util.UUID;
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
import pl.meleride.api.user.manager.UserManagerImpl;

public class PlayerPreLoginListener implements Listener {

  private UserManager userManager = new UserManagerImpl();

  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
    UUID uuid = event.getUniqueId();
    User user = this.userManager.getUser(uuid).orElseGet(() -> {
      User newUser = new UserImpl(uuid);
      this.userManager.addUser(newUser);
      return newUser;
    });

    UserInitEvent userInitEvent = new UserInitEvent(user);
    Bukkit.getPluginManager().callEvent(userInitEvent);

    if (userInitEvent.isCancelled()) {
      UserAbortEvent userAbortEvent = new UserAbortEvent(user);
      Bukkit.getPluginManager().callEvent(userAbortEvent);
      event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "Session abort.");
      return;
    }
  }

}
