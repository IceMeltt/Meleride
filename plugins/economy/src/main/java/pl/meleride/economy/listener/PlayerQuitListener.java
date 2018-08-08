package pl.meleride.economy.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.meleride.api.helper.Listener;
import pl.meleride.economy.MelerideEconomy;
import pl.meleride.economy.entity.User;

public class PlayerQuitListener implements Listener<PlayerQuitEvent> {

  private final MelerideEconomy plugin;

  public PlayerQuitListener(final MelerideEconomy plugin) {
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
