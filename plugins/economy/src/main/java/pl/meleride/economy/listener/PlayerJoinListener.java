package pl.meleride.economy.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.meleride.economy.econplayer.EconPlayer;
import pl.meleride.economy.econplayer.EconPlayerManager;

import java.util.Optional;

public class PlayerJoinListener implements Listener {

  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();

    Optional<EconPlayer> optionalEconPlayer = EconPlayerManager.getPlayer(player.getUniqueId());

    if (!optionalEconPlayer.isPresent()) {
      new EconPlayer(player.getUniqueId(), player.getName());
    } else {
      //Updatowanie nicku podczas zmiany na stronie Mojangu
      optionalEconPlayer.get().setName(player.getName());
    }
  }

}
