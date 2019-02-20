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

  //DOTYCZY SYSTEMU KONTROLI PUBLICZNYCH AUT
  @EventHandler
  public void onExitCar(MelerideCarExitEvent e) {
    if (!(e.getBasicCar().getCarType() == CarType.PUBLIC)) {
      return;
    }

    e.getBasicCar().setLastExit(System.currentTimeMillis());
  }

}
