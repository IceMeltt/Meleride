package pl.meleride.cars.car;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import pl.meleride.api.entity.IdentifiableEntity;

import java.util.List;
import java.util.UUID;

public interface Car extends IdentifiableEntity<UUID> {

  void setup();

  CarType getCarType();

  UUID getOwner();

  ArmorStand getRootArmorStand();
  List<ArmorStand> getCarSeats();

  int getMaxPassengers();

  double getMaxSpeed();

  double getSpeed();
  void setSpeed(double speed);

  long getLastExitTime();
  void setLastExitTime(long lastExitTime);

  Location getSpawnLocation();
  void setSpawnLocation(Location location);

  ItemStack getHoeItem();
  int getHoeModel();

}
