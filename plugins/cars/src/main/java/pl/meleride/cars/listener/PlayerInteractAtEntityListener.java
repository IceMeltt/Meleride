package pl.meleride.cars.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import pl.meleride.cars.MelerideCars;
import pl.meleride.cars.car.Car;
import pl.meleride.cars.car.CarType;
import pl.meleride.cars.event.MelerideCarEnterEvent;

public class PlayerInteractAtEntityListener implements Listener {

  private final MelerideCars instance;

  public PlayerInteractAtEntityListener(MelerideCars instance) {
    this.instance = instance;
  }

  @EventHandler
  public void onEnterVehicle(PlayerInteractAtEntityEvent event) {
    if (event.isCancelled()) {
      return;
    }

    if (!(event.getRightClicked() instanceof ArmorStand)) {
      return;
    }

    ArmorStand rootArmorStand = (ArmorStand) event.getRightClicked();

    if (this.instance.getCarManager().getCar(rootArmorStand.getUniqueId()) == null) {
      return;
    }

    Car car = this.instance.getCarManager().getCar(rootArmorStand.getUniqueId());

    if (car.getCarType() != CarType.PUBLIC && !car.getOwner().equals(event.getPlayer().getUniqueId())) {
      return;
    }

    System.out.println("lol1");

    for (ArmorStand armorStand : car.getCarSeats()) {
      if (armorStand.getPassengers().isEmpty()) {
        System.out.println("lol");
        armorStand.addPassenger(event.getPlayer());
        MelerideCarEnterEvent enterEvent = new MelerideCarEnterEvent(event.getPlayer(), car);
        Bukkit.getPluginManager().callEvent(enterEvent);
        break;
      }
    }

    /*car.getCarSeats().stream()
            .filter(armorStand -> armorStand.getPassengers() == null)
            .findFirst()
            .ifPresent(armorStand -> {
              System.out.println("lol");
              armorStand.addPassenger(event.getPlayer());
              MelerideCarEnterEvent enterEvent = new MelerideCarEnterEvent(event.getPlayer(), car);
              Bukkit.getPluginManager().callEvent(enterEvent);
            });*/
  }

}
