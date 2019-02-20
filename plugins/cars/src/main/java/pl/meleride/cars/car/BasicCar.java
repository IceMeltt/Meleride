package pl.meleride.cars.car;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import org.bukkit.util.Vector;
import pl.meleride.api.entity.IdentifiableEntity;
import pl.meleride.cars.api.MelerideCarsAPI;

import java.util.UUID;

public class BasicCar implements IdentifiableEntity<UUID> {

  private UUID owner;

  private ArmorStand car;
  private ArmorStand seat;

  private CarType carType;

  private final int maxPassengers;
  private int durability;

  private double max;
  private double speed;

  private final byte model;
  private final byte brand;

  private ItemStack hoeModel;

  private long lastExit;

  private final Location spawnLoc;

  public BasicCar(ArmorStand car, CarType carType, double max_speed, int durability, int maxPassengers, byte model,
                  byte brand, Location l, Player owner) {

    if (maxPassengers <= 2 && maxPassengers > 0) {
      this.maxPassengers = maxPassengers;
    } else {
      this.maxPassengers = 1;
    }

    this.seat = null;
    this.car = car;
    this.carType = carType;
    this.max = max_speed;
    this.durability = durability;
    this.model = model;
    this.brand = brand;
    this.spawnLoc = l;
    this.speed = 0;
    this.owner = owner.getUniqueId();

    this.lastExit = System.currentTimeMillis();

    setHoeModel(durability);
    spawnSeat();
  }

  @Override
  public UUID getIdentifier() {
    return car.getUniqueId();
  }

  public double getSpeed() {
    return speed;
  }

  public void setSpeed(double i) {
    this.speed = i;
  }

  private void spawnSeat() {
    ArmorStand c2 = spawnLoc.getWorld().spawn(spawnLoc.clone().add(new Vector(-1, 0, 0)), ArmorStand.class);
    c2.setHelmet(this.hoeModel);
    c2.setVisible(false);
    c2.setGravity(false);
    c2.setCustomName("Siedzonko");
    c2.setCustomNameVisible(false);
    c2.setFireTicks(0);
    c2.setMarker(true);
    MelerideCarsAPI.getSeatConnect().put(c2.getUniqueId(), this.car.getUniqueId());
    this.seat = c2;
  }

  public Location getSpawnLocation() {
    return spawnLoc;
  }

  public ArmorStand getCar() {
    return car;
  }

  public ArmorStand getSeat() {
    return seat;
  }

  public ItemStack getHoeModel() {
    return hoeModel;
  }

  public long getLastExit() {
    return lastExit;
  }

  public void setLastExit(long time) {
    this.lastExit = time;
  }

  private void setHoeModel(int dur) {
    ItemStack hoe = new ItemStack(Material.DIAMOND_HOE, 1);
    ItemMeta meta = hoe.getItemMeta();
    meta.setUnbreakable(true);
    hoe.setItemMeta(meta);
    hoe.setDurability((short) dur);
    this.hoeModel = hoe;
  }

  public byte getBrand() {
    return brand;
  }

  public byte getModel() {
    return model;
  }

  public int getDurability() {
    return durability;
  }

  public double getMaxSpeed() {
    return max;
  }

  public int getMaxPassengers() {
    return maxPassengers;
  }

  public CarType getCarType() {
    return carType;
  }

  public UUID getOwner() {
    return owner;
  }

  public void setOwner(Player owner) {
    this.owner = owner.getUniqueId();
  }

}
