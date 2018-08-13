package pl.meleride.companies.listener;

import org.bukkit.event.player.PlayerJoinEvent;
import pl.meleride.api.helper.Listener;
import pl.meleride.companies.MelerideCompanies;
import pl.meleride.companies.entity.User;

public class PlayerJoinListener implements Listener<PlayerJoinEvent> {

  private final MelerideCompanies plugin;

  public PlayerJoinListener(MelerideCompanies plugin) {
    this.plugin = plugin;
  }

  @Override
  public void performEvent(PlayerJoinEvent event) {
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
