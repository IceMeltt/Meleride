package pl.meleride.cars.objects;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import pl.meleride.cars.api.MelerideCarsAPI;
import pl.meleride.cars.enums.CarType;

public class Car {

  private CarType carType;
  private final int max_pass;
  private double max;
  private int durability;
  private final byte model;
  private final byte brand;
  private ItemStack hoeModel;
  private long lastExit;
  private ArmorStand car;
  private ArmorStand seat;
  private final Location spawnLoc;
  private double speed;
  private Player owner;

  public Car(ArmorStand car, CarType carType, double max_speed, int durability, int max_passengers, byte model,
             byte brand, Location l, Player owner) {

    if (max_passengers <= 2 && max_passengers > 0) {
      this.max_pass = max_passengers;
    } else {
      this.max_pass = 1;
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
    this.setOwner(owner);

    this.lastExit = System.currentTimeMillis();

    setHoeModel(durability);
    //this.car.setHelmet(this.hoeModel);
    spawnSeat();
  }

  public double getSpeed() {
    return speed;
  }

  public void setSpeed(double i) {
    this.speed = i;
  }

  private void spawnSeat() {
    ArmorStand c2 = spawnLoc.getWorld().spawn(spawnLoc, ArmorStand.class);
    c2.setHelmet(this.hoeModel);
    c2.setVisible(false);
    c2.setGravity(false);
    c2.setCustomName("�rSiedzonko");
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
    return max_pass;
  }

  public CarType getCarType() {
    return carType;
  }

  public Player getOwner() {
    return owner;
  }

  public void setOwner(Player owner) {
    this.owner = owner;
  }

}
