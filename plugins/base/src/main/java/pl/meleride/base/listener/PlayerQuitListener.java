package pl.meleride.base.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.meleride.api.helper.Listener;
import pl.meleride.base.MelerideBase;
import pl.meleride.base.entity.User;

public class PlayerQuitListener implements Listener<PlayerQuitEvent> {

  private final MelerideBase plugin;

  public PlayerQuitListener(MelerideBase plugin) {
    this.plugin = plugin;
  }

  @EventHandler(priority = EventPriority.LOWEST)
  @Override
  public void performEvent(PlayerQuitEvent event) {
    User user = this.plugin.getUserManager().getUser(event.getPlayer().getUniqueId()).get();

    this.plugin.getUserResource().save(user);

    this.plugin.getUserManager().removeUser(user);
  }

}
