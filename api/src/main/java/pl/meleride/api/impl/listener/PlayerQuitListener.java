package pl.meleride.api.impl.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.meleride.api.basic.User;
import pl.meleride.api.impl.event.UserQuitEvent;
import pl.meleride.api.manager.UserManager;

import javax.inject.Inject;

public class PlayerQuitListener implements Listener {

  @Inject
  private UserManager userManager;

  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    User user = this.userManager.getUser(player.getUniqueId()).get();

    UserQuitEvent userQuitEvent = new UserQuitEvent(user);
    Bukkit.getPluginManager().callEvent(userQuitEvent);
  }

}