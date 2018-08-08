package pl.meleride.cars.car.impl;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import pl.meleride.api.builder.ItemBuilder;
import pl.meleride.cars.car.Car;
import pl.meleride.cars.car.CarType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class AbstractCarImpl implements Car {

  private static final Vector[] seatsCombination = new Vector[]{new Vector(0, 0, -1), new Vector(0, 0, 0.25), new Vector(-1, 0, -1), new Vector(-1, 0, 0.25)};

  private final CarType carType;

  private final UUID owner;

  private ArmorStand rootArmorStand;

  private List<ArmorStand> carSeats = new ArrayList<>();

  private double speed;

  private long lastExitTime;

  private Location spawnLocation;

  private ItemStack hoeItem;

  public AbstractCarImpl(CarType carType, UUID owner, Location spawnLocation) {
    this.carType = carType;
    this.owner = owner;
    this.spawnLocation = spawnLocation;

    setup();
  }

  @Override
  public void setup() {
    this.hoeItem = new ItemBuilder(Material.DIAMOND_HOE, 1, getHoeModel())
            .setUnbreakable(true)
            .build();

    this.rootArmorStand = this.spawnLocation.getWorld().spawn(spawnLocation, ArmorStand.class);
    this.rootArmorStand.setVisible(false);
    this.rootArmorStand.setGravity(true);
    this.rootArmorStand.setCustomName("Car");
    this.rootArmorStand.setCustomNameVisible(false);
    this.rootArmorStand.setFireTicks(0);
    this.rootArmorStand.setMarker(false);
    this.rootArmorStand.setHelmet(this.hoeItem);

    for (int i = 0; i < getMaxPassengers(); i++) {
      ArmorStand seat = this.spawnLocation.getWorld().spawn(this.spawnLocation.clone().add(seatsCombination[i]), ArmorStand.class);
      seat.setVisible(false);
      seat.setGravity(false);
      seat.setCustomName("Seat");
      seat.setCustomNameVisible(false);
      seat.setFireTicks(0);
      seat.setMarker(true);

      this.carSeats.add(seat);
    }
  }

  @Override
  public CarType getCarType() {
    return this.carType;
  }

  @Override
  public UUID getOwner() {
    return this.owner;
  }

  @Override
  public ArmorStand getRootArmorStand() {
    return this.rootArmorStand;
  }

  @Override
  public List<ArmorStand> getCarSeats() {
    return this.carSeats;
  }

  @Override
  public int getMaxPassengers() {
    return 1;
  }

  @Override
  public double getMaxSpeed() {
    return 2;
  }

  @Override
  public double getSpeed() {
    return this.speed;
  }

  @Override
  public void setSpeed(double speed) {
    this.speed = speed;
  }

  @Override
  public long getLastExitTime() {
    return this.lastExitTime;
  }

  @Override
  public void setLastExitTime(long lastExitTime) {
    this.lastExitTime = lastExitTime;
  }

  @Override
  public Location getSpawnLocation() {
    return this.spawnLocation;
  }

  @Override
  public void setSpawnLocation(Location location) {
    this.spawnLocation = location;
  }

  @Override
  public ItemStack getHoeItem() {
    return this.hoeItem;
  }

  @Override
  public int getHoeModel() {
    return 105;
  }

  @Override
  public UUID getIdentifier() {
    return this.rootArmorStand.getUniqueId();
  }

}
