package pl.meleride.cars.car.impl;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftArmorStand;
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

  private static final double[] SEATS_COMBINATION = new double[]{180, 135, 90, 45};

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
      ArmorStand seat = this.spawnLocation.getWorld().spawn(this.spawnLocation.clone(), ArmorStand.class);
      seat.setVisible(false);
      seat.setGravity(false);
      seat.setCustomName("Seat" + i);
      seat.setCustomNameVisible(false);
      seat.setFireTicks(0);
      seat.setMarker(true);

      this.carSeats.add(seat);
    }
  }

  @Override
  public void update() {
    for (int i = 0; i < this.carSeats.size(); i++) {
      Vector offset = new Vector(Math.cos(SEATS_COMBINATION[i] + this.rootArmorStand.getLocation().getPitch()) * 2, 0, Math.sin(SEATS_COMBINATION[i] + this.rootArmorStand.getLocation().getPitch()) * 2);
      Location location = this.rootArmorStand.getLocation().add(offset);
      ((CraftArmorStand) this.carSeats.get(i)).getHandle().setPosition(location.getX(), location.getY(), location.getZ());
      this.carSeats.get(i).getLocation().setPitch(this.rootArmorStand.getLocation().getPitch());
      this.carSeats.get(i).getLocation().setYaw(this.rootArmorStand.getLocation().getYaw());
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
