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
import pl.meleride.api.storage.dao.StorageDao;
import pl.meleride.api.storage.dao.UserDaoImpl;
import pl.meleride.api.user.User;
import pl.meleride.api.user.UserImpl;
import pl.meleride.api.user.event.UserAbortEvent;
import pl.meleride.api.user.event.UserLoadEvent;
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
    User user = this.userManager.getUser(player).orElseGet(() -> {
      User newUser = new UserImpl(player);
      this.userManager.addUser(newUser);
      return newUser;
    });

    UserLoadEvent userLoadEvent = new UserLoadEvent(user);

    if (userLoadEvent.isCancelled()) {
      UserAbortEvent userAbortEvent = new UserAbortEvent(user);
      Bukkit.getPluginManager().callEvent(userAbortEvent);
      event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "Session abort.");
      return;
    }

    StorageDao<User> dao = new UserDaoImpl(instance);
    try {
      if(!player.hasPlayedBefore()) {
        dao.update(user);
      } else {
        dao.download(user);
      }
    } catch(SQLException | StorageException e) {
      Bukkit.getLogger().severe("Wystąpił BARDZO POTEŻNY błąd w ładowaniu gracza!!1");
      e.printStackTrace();
    }

    Bukkit.getPluginManager().callEvent(userLoadEvent);
  }

}
