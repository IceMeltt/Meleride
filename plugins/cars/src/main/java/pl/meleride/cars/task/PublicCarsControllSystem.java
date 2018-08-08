package pl.meleride.cars.task;

import org.bukkit.scheduler.BukkitRunnable;

import pl.meleride.cars.api.MelerideCarsAPI;
import pl.meleride.cars.car.CarType;
import pl.meleride.cars.car.BasicCar;

public class PublicCarsControllSystem extends BukkitRunnable {

  public void run() {
    for (BasicCar basicCar : MelerideCarsAPI.getCarsMap().values()) {
      if (basicCar.getCarType() == CarType.PUBLIC) {
        if (basicCar.getCar().getPassenger() == null
                && basicCar.getCar().getLocation().distance(basicCar.getSpawnLocation()) > 2) {
          if (System.currentTimeMillis() - basicCar.getLastExit() > 60000) {
            basicCar.getCar().remove();
            MelerideCarsAPI.getCarsMap().remove(basicCar.getCar().getUniqueId());
            //TODO: SPAWN NOWEGO AUTA ALBO PO PROSTU ZAMIAST USUWAC GO TO TEPAC DO MIEJSCA STARTOWEGO
            continue;
          }
        }
      }
    }
  }

}
