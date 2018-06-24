package pl.meleride.api.user.caller;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.meleride.api.MelerideAPI;
import pl.meleride.api.storage.sql.hikari.SQLHikariStorage;
import pl.meleride.api.user.User;
import pl.meleride.api.user.UserImpl;
import pl.meleride.api.user.event.UserQuitEvent;
import pl.meleride.api.user.manager.UserDatabaseSteward;
import pl.meleride.api.user.manager.UserManager;
import pl.meleride.api.user.manager.UserManagerImpl;

public class PlayerQuitListener implements Listener {

  private final MelerideAPI instance;
  private UserManager userManager = new UserManagerImpl();

  public PlayerQuitListener(final MelerideAPI instance) {
    this.instance = instance;
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    User user;
    if(this.userManager.getUser(player.getUniqueId()).isPresent()) {
      user = this.userManager.getUser(player.getUniqueId()).get();
    } else {
      if(this.userManager.getUser(player.getName()).isPresent()) {
        user = this.userManager.getUser(player.getName()).get();
      } else {
        //TODO Help wanted!!
        user = new UserImpl(player.getUniqueId());
        user.setName(player.getName());
      }
    }
    SQLHikariStorage storage = this.instance.getStorage();

    UserQuitEvent userQuitEvent = new UserQuitEvent(user);

    UserDatabaseSteward steward = new UserDatabaseSteward(player.getUniqueId(), user, storage);
    steward.savePlayer();

    Bukkit.getPluginManager().callEvent(userQuitEvent);
  }

}
