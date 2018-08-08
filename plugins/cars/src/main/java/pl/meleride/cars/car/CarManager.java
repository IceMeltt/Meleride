package pl.meleride.cars.car;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CarManager {

  private final ConcurrentMap<UUID, BasicCar> uuidCarConcurrentMap = new ConcurrentHashMap<>(16, 0.9F, 1);

  public Optional<BasicCar> getCar(UUID uuid) {
    return Optional.of(uuidCarConcurrentMap.getOrDefault(uuid, null));
  }

  public void addCar(BasicCar basicCar) {
    if (uuidCarConcurrentMap.containsKey(basicCar.getIdentifier())) {
      return;
    }

    uuidCarConcurrentMap.put(basicCar.getIdentifier(), basicCar);
  }

}
