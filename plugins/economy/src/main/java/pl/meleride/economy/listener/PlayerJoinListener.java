package pl.meleride.economy.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.meleride.economy.econplayer.EconPlayer;

public class PlayerJoinListener implements Listener {

  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();

    if (EconPlayer.getPlayer(player.getUniqueId()) == null) {
      new EconPlayer(player.getUniqueId(), player.getName());
    } else {
      //Updatowanie nicku podczas zmiany na stronie Mojangu
      EconPlayer.getPlayer(player.getUniqueId()).setName(player.getName());
    }
  }

}
