package pl.meleride.api.user.caller;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.meleride.api.MelerideAPI;
import pl.meleride.api.storage.userflow.FlowInspector;
import pl.meleride.api.user.User;
import pl.meleride.api.user.event.UserQuitEvent;
import pl.meleride.api.user.manager.UserManager;
import pl.meleride.api.user.manager.UserManagerImpl;
import pl.meleride.api.storage.userflow.UserInspector;

public class PlayerQuitListener implements Listener {

  private final MelerideAPI instance;
  private UserManager userManager = new UserManagerImpl();

  public PlayerQuitListener(final MelerideAPI instance) {
    this.instance = instance;
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    FlowInspector<User> inspector = new UserInspector(instance);
    inspector.quit(player);

    UserQuitEvent userQuitEvent = new UserQuitEvent(inspector.getUser());
    Bukkit.getPluginManager().callEvent(userQuitEvent);
  }

}
