package pl.meleride.base.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.meleride.api.helper.Listener;
import pl.meleride.base.MelerideBase;
import pl.meleride.base.entity.User;

public class PlayerJoinListener implements Listener<PlayerJoinEvent> {

  private final MelerideBase plugin;

  public PlayerJoinListener(MelerideBase plugin) {
    this.plugin = plugin;
  }

  @EventHandler(priority = EventPriority.LOWEST)
  @Override
  public void performEvent(PlayerJoinEvent event) {
    User user = this.plugin.getUserManager().getUser(event.getPlayer().getUniqueId()).get();

    if (!user.getName().isPresent()) {
      user.setName(event.getPlayer().getName());
    }

    if(!event.getPlayer().hasPlayedBefore()) {
      this.plugin.getUserResource().save(user);
    } else {
      this.plugin.getUserResource().load(user);
    }
  }

}
