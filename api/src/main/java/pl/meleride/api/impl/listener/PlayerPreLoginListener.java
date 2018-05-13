package pl.meleride.api.impl.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import pl.meleride.api.MelerideAPI;
import pl.meleride.api.basic.User;
import pl.meleride.api.impl.event.UserAbortEvent;
import pl.meleride.api.impl.event.UserInitEvent;

public class PlayerPreLoginListener implements Listener {

  private final MelerideAPI plugin;

  public PlayerPreLoginListener(MelerideAPI plugin) {
    this.plugin = plugin;
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
    User user = this.plugin.getUserManager().getUser(event.getUniqueId());

    UserInitEvent userInitEvent = new UserInitEvent(user);
    Bukkit.getPluginManager().callEvent(userInitEvent);

    if (userInitEvent.isCancelled()) {
      UserAbortEvent userAbortEvent = new UserAbortEvent(user);
      Bukkit.getPluginManager().callEvent(userAbortEvent);
      event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "Session abort.");
      return;
    }

    this.plugin.getUserManager().addUser(user);
  }

}
