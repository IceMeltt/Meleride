package pl.meleride.companies.listener;

import org.bukkit.event.player.PlayerQuitEvent;
import pl.meleride.api.helper.Listener;
import pl.meleride.companies.MelerideCompanies;
import pl.meleride.companies.entity.User;

public final class PlayerQuitListener implements Listener<PlayerQuitEvent> {

  private final MelerideCompanies plugin;

  public PlayerQuitListener(MelerideCompanies plugin) {
    this.plugin = plugin;
  }

  @Override
  public void performEvent(PlayerQuitEvent event) {
    User user = this.plugin.getUserManager().getUser(event.getPlayer().getUniqueId()).get();

    this.plugin.getUserResource().save(user);
    this.plugin.getUserManager().removeUser(user);
  }

}
