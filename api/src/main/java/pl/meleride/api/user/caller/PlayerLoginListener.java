package pl.meleride.api.user.caller;

import java.sql.SQLException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import pl.meleride.api.MelerideAPI;
import pl.meleride.api.storage.StorageException;
import pl.meleride.api.storage.sql.hikari.SQLHikariStorage;
import pl.meleride.api.user.User;
import pl.meleride.api.user.event.UserAbortEvent;
import pl.meleride.api.user.event.UserLoadEvent;
import pl.meleride.api.user.manager.UserDatabaseSteward;
import pl.meleride.api.user.manager.UserManager;
import pl.meleride.api.user.manager.UserManagerImpl;

public class PlayerLoginListener implements Listener {

  private final MelerideAPI instance;
  private UserManager userManager = new UserManagerImpl();

  public PlayerLoginListener(final MelerideAPI instance) {
    this.instance = instance;
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerLogin(PlayerLoginEvent event) {
    Player player = event.getPlayer();
    if(!this.userManager.getUser(player.getUniqueId()).isPresent()) {
      this.userManager.createUser(player);
    }
    User user = this.userManager.getUser(player.getUniqueId()).get();

    UserLoadEvent userLoadEvent = new UserLoadEvent(user);

    if (userLoadEvent.isCancelled()) {
      UserAbortEvent userAbortEvent = new UserAbortEvent(user);
      Bukkit.getPluginManager().callEvent(userAbortEvent);
      event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "Session abort.");
      return;
    }

    SQLHikariStorage storage = instance.getStorage();
    UserDatabaseSteward setter = new UserDatabaseSteward(player.getUniqueId(), user, storage);
    try {
      if(!player.hasPlayedBefore()) {
        setter.makePlayer(player);
      } else {
        setter.setName(player.getName());
        setter.setDiseases();
      }
    } catch(StorageException | SQLException e) {
      Bukkit.getLogger().severe("Wystąpił BARDZO POTEŻNY błąd w ładowaniu gracza!!1");
      e.printStackTrace();
    }

    Bukkit.getPluginManager().callEvent(userLoadEvent);
  }

}
