package pl.meleride.cars.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import pl.meleride.cars.MelerideCars;

public class PlayerArmorStandManipulateListener implements Listener {

  private final MelerideCars instance;

  public PlayerArmorStandManipulateListener(MelerideCars instance) {
    this.instance = instance;
  }

  @EventHandler
  public void onArmorStandManipulate(PlayerArmorStandManipulateEvent event) {
    if(instance.getCarManager().getCar(event.getRightClicked().getUniqueId()) == null) {
      return;
    }

    event.setCancelled(true);
  }

}
