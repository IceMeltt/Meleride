package pl.meleride.economy.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.meleride.api.helper.Listener;
import pl.meleride.economy.MelerideEconomy;
import pl.meleride.economy.entity.User;

public class PlayerJoinListener implements Listener<PlayerJoinEvent> {

  private final MelerideEconomy plugin;

  public PlayerJoinListener(final MelerideEconomy plugin) {
    this.plugin = plugin;
  }

  @EventHandler(priority = EventPriority.LOWEST)
  @Override
  public void performEvent(PlayerJoinEvent event) {
    User user = this.plugin.getUserManager().getUser(event.getPlayer().getUniqueId()).get();

    if (!user.getName().isPresent()) {
      user.setName(event.getPlayer().getName());
    }
  }

}