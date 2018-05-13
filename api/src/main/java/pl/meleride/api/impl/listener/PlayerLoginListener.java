package pl.meleride.api.impl.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import pl.meleride.api.MelerideAPI;
import pl.meleride.api.basic.User;
import pl.meleride.api.impl.event.UserLoadEvent;

public class PlayerLoginListener implements Listener {

  private final MelerideAPI plugin;

  public PlayerLoginListener(MelerideAPI plugin) {
    this.plugin = plugin;
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerLogin(PlayerLoginEvent event) {
    Player player = event.getPlayer();
    User user = this.plugin.getUserManager().getUser(player.getUniqueId());
    UserLoadEvent userLoadEvent = new UserLoadEvent(user);

    Bukkit.getPluginManager().callEvent(userLoadEvent);
  }

}
