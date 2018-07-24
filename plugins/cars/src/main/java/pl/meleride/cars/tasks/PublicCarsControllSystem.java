package pl.meleride.cars.tasks;

import org.bukkit.scheduler.BukkitRunnable;

import pl.meleride.cars.api.MelerideCarsAPI;
import pl.meleride.cars.enums.CarType;
import pl.meleride.cars.objects.Car;

public class PublicCarsControllSystem extends BukkitRunnable {

	public void run() {
		for (Car car : MelerideCarsAPI.getCarsMap().values()) {
			if (car.getCarType() == CarType.PUBLIC) {
				if (car.getCar().getPassenger() == null
						&& car.getCar().getLocation().distance(car.getSpawnLocation()) > 2) {
					if (System.currentTimeMillis() - car.getLastExit() > 60000) {
						car.getCar().remove();
						MelerideCarsAPI.getCarsMap().remove(car.getCar().getUniqueId());
						//TODO: SPAWN NOWEGO AUTA ALBO PO PROSTU ZAMIAST USUWAC GO TO TEPAC DO MIEJSCA STARTOWEGO
						continue;
					}
				}
			}
		}
	}

}
