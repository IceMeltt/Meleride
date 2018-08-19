package pl.meleride.user.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.meleride.api.helper.Listener;
import pl.meleride.api.storage.StorageException;
import pl.meleride.user.MelerideUser;
import pl.meleride.user.entity.User;

public class PlayerJoinListener implements Listener<PlayerJoinEvent> {

  private final MelerideUser plugin;

  public PlayerJoinListener(final MelerideUser plugin) {
    this.plugin = plugin;
  }

  @EventHandler(priority = EventPriority.LOWEST)
  @Override
  public void performEvent(PlayerJoinEvent event) throws StorageException {
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
