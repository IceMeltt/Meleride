package pl.meleride.api.user.caller;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.meleride.api.storage.resource.Resource;
import pl.meleride.api.user.User;
import pl.meleride.api.user.event.UserQuitEvent;
import pl.meleride.api.user.manager.UserManager;

import javax.inject.Inject;

public class PlayerQuitListener implements Listener {

  private final UserManager userManager;
  private final Resource<User> resource;

  @Inject
  PlayerQuitListener(UserManager userManager, Resource<User> resource) {
    this.userManager = userManager;
    this.resource = resource;
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    User user = this.userManager.getUser(player.getUniqueId()).get();

    UserQuitEvent userQuitEvent = new UserQuitEvent(user);
    Bukkit.getPluginManager().callEvent(userQuitEvent);

    this.resource.save(user);
  }

}
