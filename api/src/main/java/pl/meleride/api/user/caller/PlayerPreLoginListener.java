package pl.meleride.api.user.caller;

import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import pl.meleride.api.MelerideAPI;
import pl.meleride.api.user.User;
import pl.meleride.api.user.event.UserAbortEvent;
import pl.meleride.api.user.event.UserInitEvent;
import pl.meleride.api.user.manager.UserManager;
import pl.meleride.api.user.manager.UserManagerImpl;

public class PlayerPreLoginListener implements Listener {

  private MelerideAPI instance;
  private UserManager userManager = new UserManagerImpl();

  public PlayerPreLoginListener(final MelerideAPI instance) {
    this.instance = instance;
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
    UUID uuid = event.getUniqueId();
    String name = event.getName();
    if(!this.userManager.getUser(uuid).isPresent()) {
      this.userManager.createUser(uuid, name);
    }
    User user = this.userManager.getUser(uuid).get();

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
