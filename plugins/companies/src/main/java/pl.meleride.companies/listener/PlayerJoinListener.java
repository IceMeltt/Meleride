package pl.meleride.companies.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.meleride.api.helper.Listener;
import pl.meleride.api.storage.StorageException;
import pl.meleride.companies.MelerideCompanies;
import pl.meleride.companies.entity.User;

public final class PlayerJoinListener implements Listener<PlayerJoinEvent> {

  private final MelerideCompanies plugin;

  public PlayerJoinListener(MelerideCompanies plugin) {
    this.plugin = plugin;
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  @Override
  public void performEvent(PlayerJoinEvent event) throws StorageException {
    User user = this.plugin.getUserManager().getUser(event.getPlayer().getUniqueId()).get();

    if (!user.getName().isPresent()) {
      user.setName(event.getPlayer().getName());
    }

    if (!event.getPlayer().hasPlayedBefore()) {
      this.plugin.getUserResource().save(user);
      return;
    }

    this.plugin.getUserResource().load(user);
  }

}
