package pl.meleride.economy.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import pl.meleride.api.storage.userflow.FlowInspector;
import pl.meleride.economy.MelerideEconomy;
import pl.meleride.economy.user.EconomyUser;
import pl.meleride.economy.user.EconomyUserInspector;

public class PlayerLoginListener implements Listener {

  private final MelerideEconomy instance;

  public PlayerLoginListener(final MelerideEconomy instance) {
  this.instance = instance;
}

  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerLogin(PlayerLoginEvent event) {
    Player player = event.getPlayer();
    FlowInspector<EconomyUser> inspector = new EconomyUserInspector(instance);
    inspector.join(player);
  }

}
