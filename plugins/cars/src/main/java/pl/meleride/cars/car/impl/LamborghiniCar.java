package pl.meleride.cars.car.impl;

import org.bukkit.Location;
import pl.meleride.cars.car.CarType;

import java.util.UUID;

public class LamborghiniCar extends AbstractCarImpl {

  public LamborghiniCar(CarType carType, UUID owner, Location spawnLocation) {
    super(carType, owner, spawnLocation);
  }

  @Override
  public int getMaxPassengers() {
    return 2;
  }

}
