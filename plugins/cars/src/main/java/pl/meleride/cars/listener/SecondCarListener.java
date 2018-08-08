package pl.meleride.cars.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import pl.meleride.cars.api.MelerideCarsAPI;
import pl.meleride.cars.car.CarType;

import pl.meleride.cars.event.MelerideCarEnterEvent;
import pl.meleride.cars.event.MelerideCarExitEvent;
import pl.meleride.cars.car.BasicCar;

public class SecondCarListener implements Listener {

  @SuppressWarnings("deprecation")
  @EventHandler
  public void onEnterVehicle(PlayerInteractAtEntityEvent e) {
    if (!(e.getRightClicked() instanceof ArmorStand)) {
      return;
    }

    ArmorStand as = (ArmorStand) e.getRightClicked();

    if (!MelerideCarsAPI.getCarsMap().containsKey(as.getUniqueId())) {
      return;
    }

    BasicCar basicCar = MelerideCarsAPI.getCarsMap().get(as.getUniqueId());
    MelerideCarEnterEvent event = new MelerideCarEnterEvent(e.getPlayer(), basicCar);
    Bukkit.getPluginManager().callEvent(event);

    if (event.isCancelled()) {
      return;
    }

    if (basicCar.getCarType() != CarType.PUBLIC) {
      if (basicCar.getOwner() != e.getPlayer().getUniqueId()) return;
    }

    basicCar.getSeat().setPassenger(e.getPlayer());
  }

  // BLOKADA BUGOWANIA AUT
  @EventHandler
  public void onArmorStandManipulate(PlayerArmorStandManipulateEvent e) {
    if (!MelerideCarsAPI.getCarsMap().containsKey(e.getRightClicked().getUniqueId())) {
      return;
    }

    e.setCancelled(true);
  }

  @EventHandler
  public void onEnterCar(MelerideCarEnterEvent e) {

  }

  //DOTYCZY SYSTEMU KONTROLI PUBLICZNYCH AUT
  @EventHandler
  public void onExitCar(MelerideCarExitEvent e) {
    if (!(e.getBasicCar().getCarType() == CarType.PUBLIC)) {
      return;
    }

    e.getBasicCar().setLastExit(System.currentTimeMillis());
  }

}
