package pl.meleride.economy.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.meleride.api.helper.Listener;
import pl.meleride.api.storage.StorageException;
import pl.meleride.economy.MelerideEconomy;
import pl.meleride.economy.currency.Currency;
import pl.meleride.economy.entity.User;

public class PlayerJoinListener implements Listener<PlayerJoinEvent> {

  private final MelerideEconomy plugin;

  public PlayerJoinListener(final MelerideEconomy plugin) {
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
      } else {
        this.plugin.getUserResource().load(user);
    }

    if(user.getPocketBalance().get(Currency.PLN) == null) {
      plugin.getUserManager().addMoney(user, Currency.PLN, 0.0);
    }
  }

}
