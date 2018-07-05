package pl.meleride.economy.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.meleride.api.storage.userflow.FlowInspector;
import pl.meleride.economy.MelerideEconomy;
import pl.meleride.economy.user.EconomyUser;
import pl.meleride.economy.user.EconomyUserInspector;

public class PlayerQuitListener implements Listener {

  private final MelerideEconomy instance;

  public PlayerQuitListener(final MelerideEconomy instance) {
    this.instance = instance;
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    FlowInspector<EconomyUser> inspector = new EconomyUserInspector(instance);
    inspector.quit(player);

  }

}
