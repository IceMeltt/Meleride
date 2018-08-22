package pl.meleride.user.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.meleride.api.helper.Listener;
import pl.meleride.user.MelerideUser;
import pl.meleride.user.entity.User;

public class PlayerQuitListener implements Listener<PlayerQuitEvent> {

  private final MelerideUser plugin;

  public PlayerQuitListener(final MelerideUser plugin) {
    this.plugin = plugin;
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  @Override
  public void performEvent(PlayerQuitEvent event) {
    User user = this.plugin.getUserManager().getUser(event.getPlayer().getUniqueId()).get();

    this.plugin.getUserResource().save(user);

    this.plugin.getUserManager().removeUser(user);
  }

}
