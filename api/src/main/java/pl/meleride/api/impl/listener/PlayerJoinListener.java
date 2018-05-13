package pl.meleride.api.impl.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.meleride.api.MelerideAPI;
import pl.meleride.api.basic.User;
import pl.meleride.api.impl.event.UserAbortEvent;

public class PlayerJoinListener implements Listener {

  private final MelerideAPI plugin;

  public PlayerJoinListener(MelerideAPI plugin) {
    this.plugin = plugin;
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    User user = this.plugin.getUserManager().getUser(player.getUniqueId());
    UserAbortEvent userAbortEvent = new UserAbortEvent(user);

    Bukkit.getPluginManager().callEvent(userAbortEvent);
  }

}
