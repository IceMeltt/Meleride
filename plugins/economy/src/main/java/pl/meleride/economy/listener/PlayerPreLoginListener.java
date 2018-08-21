package pl.meleride.economy.listener;

import java.util.Optional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import pl.meleride.api.helper.Listener;
import pl.meleride.economy.MelerideEconomy;
import pl.meleride.economy.entity.User;
import pl.meleride.economy.entity.impl.UserImpl;

public class PlayerPreLoginListener implements Listener<AsyncPlayerPreLoginEvent> {

  private final MelerideEconomy plugin;

  public PlayerPreLoginListener(final MelerideEconomy plugin) {
    this.plugin = plugin;
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  @Override
  public void performEvent(AsyncPlayerPreLoginEvent event) {
    Optional<User> optionalUser = this.plugin.getUserManager().getUser(event.getUniqueId());

    if (!optionalUser.isPresent()) {
      User user = new UserImpl(event.getUniqueId());
      this.plugin.getUserManager().addUser(user);
      return;
    }

    this.plugin.getUserManager().addUser(optionalUser.get());
  }

}
