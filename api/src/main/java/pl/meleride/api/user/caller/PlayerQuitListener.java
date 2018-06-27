package pl.meleride.api.user.caller;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.meleride.api.MelerideAPI;
import pl.meleride.api.storage.StorageException;
import pl.meleride.api.storage.dao.UserDao;
import pl.meleride.api.storage.dao.UserDaoImpl;
import pl.meleride.api.user.User;
import pl.meleride.api.user.UserImpl;
import pl.meleride.api.user.event.UserQuitEvent;
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
    User user = this.userManager.getUser(player).orElseGet(() -> {
      User newUser = new UserImpl(player);
      this.userManager.addUser(newUser);
      return newUser;
    });
    UserDao dao = new UserDaoImpl(instance);
    try {
      dao.update(user);
    } catch(StorageException e) {
      Bukkit.getLogger().severe("Wystąpił BARDZO POTEŻNY błąd w aktualizacji gracza!!1");
      e.printStackTrace();
    }

    UserQuitEvent userQuitEvent = new UserQuitEvent(user);
    Bukkit.getPluginManager().callEvent(userQuitEvent);
  }

}
