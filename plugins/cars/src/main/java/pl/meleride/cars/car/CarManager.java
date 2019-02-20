package pl.meleride.cars.car;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CarManager {

  private final ConcurrentMap<UUID, Car> uuidCarConcurrentMap = new ConcurrentHashMap<>(16, 0.9F, 1);
  private final ConcurrentMap<UUID, Car> seatsCarConcurrentMap = new ConcurrentHashMap<>(16, 0.9F, 1);

  public Car getCar(UUID uuid) {
    return uuidCarConcurrentMap.getOrDefault(uuid, null);
  }

  public Car getCarBySeat(UUID uuid) {
    return seatsCarConcurrentMap.getOrDefault(uuid, null);
  }

  public void addCar(Car car) {
    if (uuidCarConcurrentMap.containsKey(car.getIdentifier())) {
      return;
    }

    uuidCarConcurrentMap.put(car.getIdentifier(), car);

    car.getCarSeats().forEach(armorStand -> seatsCarConcurrentMap.put(armorStand.getUniqueId(), car));
  }

}
