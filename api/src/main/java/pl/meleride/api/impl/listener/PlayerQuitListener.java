package pl.meleride.api.impl.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.meleride.api.MelerideAPI;
import pl.meleride.api.basic.User;
import pl.meleride.api.impl.event.UserQuitEvent;
import pl.meleride.api.impl.i18n.MessageBundle;
import pl.meleride.api.impl.type.MessageType;

public class PlayerQuitListener implements Listener {

  private final MelerideAPI plugin;

  public PlayerQuitListener(MelerideAPI plugin) {
    this.plugin = plugin;
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();

    MessageBundle.create("duzyChuj")
        .withField("CHUJ", "duzy chuj")
        .target(MessageType.CHAT)
        .sendTo(player);

    User user = this.plugin.getUserManager().getUser(player.getUniqueId()).get();

    UserQuitEvent userQuitEvent = new UserQuitEvent(user);
    Bukkit.getPluginManager().callEvent(userQuitEvent);
  }

}
