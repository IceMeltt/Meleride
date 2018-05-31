package pl.meleride.api.user.caller;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import pl.meleride.api.exception.UserLackException;
import pl.meleride.api.i18n.MessageBundler;
import pl.meleride.api.user.User;
import pl.meleride.api.user.event.UserAbortEvent;
import pl.meleride.api.user.event.UserLoadEvent;
import pl.meleride.api.user.manager.UserManager;

import javax.inject.Inject;
import java.util.Optional;

public class PlayerLoginListener implements Listener {

  private final UserManager userManager;

  @Inject
  PlayerLoginListener(UserManager userManager) {
    this.userManager = userManager;
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerLogin(PlayerLoginEvent event) {
    Player player = event.getPlayer();
    Optional<User> optionalUser = this.userManager.getUser(player.getUniqueId());

    if (!optionalUser.isPresent()) {
      event.disallow(PlayerLoginEvent.Result.KICK_OTHER, MessageBundler.create("messages.login-error").toString());
      throw new UserLackException();
    }

    User user = optionalUser.get();

    if (!user.getName().isPresent()) {
      user.setName(player.getName());
    }

    UserLoadEvent userLoadEvent = new UserLoadEvent(user);

    if (userLoadEvent.isCancelled()) {
      UserAbortEvent userAbortEvent = new UserAbortEvent(user);
      Bukkit.getPluginManager().callEvent(userAbortEvent);
      event.disallow(PlayerLoginEvent.Result.KICK_OTHER, MessageBundler.create("messages.session-abort").toString());
      return;
    }

    Bukkit.getPluginManager().callEvent(userLoadEvent);
  }

}
