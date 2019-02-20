package pl.meleride.cars.packet;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftArmorStand;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;

import org.bukkit.util.Vector;
import pl.meleride.cars.MelerideCars;
import pl.meleride.cars.api.MelerideCarsAPI;
import pl.meleride.cars.car.BasicCar;
import pl.meleride.cars.car.Car;
import pl.meleride.cars.util.SomeShit;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayInSteerVehicle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CarPacketListener extends PacketAdapter {

  private final MelerideCars instance;

  private final double ROTATION_SPEED = 2;
  private final List<Material> materialList = new ArrayList<>();

  public CarPacketListener(MelerideCars plugin, ListenerPriority listenerPriority, PacketType[] types) {
    super(plugin, listenerPriority, types);

    this.instance = plugin;

    materialList.addAll(Arrays.asList(
            Material.RED_ROSE,
            Material.DEAD_BUSH,
            Material.DOUBLE_PLANT,
            Material.BROWN_MUSHROOM,
            Material.RED_MUSHROOM,
            Material.YELLOW_FLOWER,
            Material.SUGAR_CANE_BLOCK
    ));
  }

  public void onPacketReceiving(PacketEvent event) {
    if (!(event.getPacketType() == PacketType.Play.Client.STEER_VEHICLE
            && event.getPlayer().getVehicle() instanceof ArmorStand)) {
      return;
    }

    if (!event.getPlayer().getVehicle().getCustomName().startsWith("Seat0")) {
      return;
    }

    PacketPlayInSteerVehicle packetPlayInSteerVehicle = (PacketPlayInSteerVehicle) event.getPacket().getHandle();
    PacketContainer packetContainer = event.getPacket();

    ArmorStand armorStand = (ArmorStand) event.getPlayer().getVehicle();

    if (this.instance.getCarManager().getCarBySeat(armorStand.getUniqueId()) == null) {
      return;
    }

    Car car = this.instance.getCarManager().getCarBySeat(armorStand.getUniqueId());

    ArmorStand rootCarArmorStand = car.getRootArmorStand();

    if (!(armorStand.getPassengers().get(0) instanceof Player)) {
      return;
    }

    Player player = event.getPlayer();
    Block block = rootCarArmorStand.getLocation().getBlock();

    // PAKIECIKI
    float forward = packetContainer.getFloat().read(1); // RUCH PRZOD/TYL
    float sideways = packetPlayInSteerVehicle.a(); // RUCH NA BOKI
    boolean space = packetPlayInSteerVehicle.c(); // SPACJA

    if (forward > 0) { // [W]
      if (!(car.getSpeed() >= car.getMaxSpeed())) {
        car.setSpeed(car.getSpeed() + 0.01);
      }
    } else if (forward < 0) { // [S]
      if (!(car.getSpeed() <= -(car.getMaxSpeed()/2))) {
        car.setSpeed(car.getSpeed() - 0.01);
      }
				/*if (MelerideCarsAPI.getCarsMap().get(car2.getUniqueId()).getSpeed() >= 0.1) {
					MelerideCarsAPI.getCarsMap().get(car2.getUniqueId()).setSpeed(MelerideCarsAPI.getCarsMap().get(car2.getUniqueId()).getSpeed()-0.01);
				} else {
					MelerideCarsAPI.getCarsMap().get(car2.getUniqueId()).setSpeed(0);
				}*/
    }

    //HAMOWANKO
    if (space) {
      if (car.getSpeed() != 0) {
        if (car.getSpeed() > 0) {
          if (car.getSpeed() - 0.04 < 0) {
            car.setSpeed(0);
          } else {
            car.setSpeed(car.getSpeed() - 0.04);
          }
        } else if (car.getSpeed() < 0) {
          if (car.getSpeed() + 0.04 > 0) {
            car.setSpeed(0);
          } else {
            car.setSpeed(car.getSpeed() + 0.04);
          }
        }
      }
    }

    if (sideways > 0) { // Side[A]
      if (rootCarArmorStand.getVelocity().length() > 0.1) {
        ((CraftArmorStand) rootCarArmorStand).getHandle().yaw = (float) (rootCarArmorStand.getLocation().getYaw() + (ROTATION_SPEED * -1));
        ((CraftArmorStand) armorStand).getHandle().yaw = (float) (rootCarArmorStand.getLocation().getYaw() + (ROTATION_SPEED * -1));
      }

    } else if (sideways < 0) { // Side[D]
      if (rootCarArmorStand.getVelocity().length() > 0.1) {
        ((CraftArmorStand) rootCarArmorStand).getHandle().yaw = (float) (rootCarArmorStand.getLocation().getYaw() + ROTATION_SPEED);
        ((CraftArmorStand) armorStand).getHandle().yaw = (float) (rootCarArmorStand.getLocation().getYaw() + ROTATION_SPEED);

      }
    }

    rootCarArmorStand.setVelocity(rootCarArmorStand.getLocation().getDirection().multiply(car.getSpeed()).setY(-2));

    // CHECK X AXIS
    if ((block.getRelative(1, 1, 0).getType() == Material.AIR && block.getRelative(1, 0, 0).getType() != Material.AIR)) {
      getRelative(player, block, 1, 0, car);
    }
    // CHECK -X AXIS
    if ((block.getRelative(-1, 1, 0).getType() == Material.AIR && block.getRelative(-1, 0, 0).getType() != Material.AIR)) {
      getRelative(player, block, -1, 0, car);
    }
    // CHECK Z AXIS
    if ((block.getRelative(0, 1, 1).getType() == Material.AIR && block.getRelative(0, 0, 1).getType() != Material.AIR)) {
      getRelative(player, block, 0, 1, car);
    }
    // CHECK -Z AXIS
    if ((block.getRelative(0, 1, -1).getType() == Material.AIR && block.getRelative(0, 0, -1).getType() != Material.AIR)) {
      getRelative(player, block, 0, -1, car);
    }

    //((CraftArmorStand) armorStand).getHandle().setPosition(rootCarArmorStand.getLocation().getX(), rootCarArmorStand.getLocation().getY(),
    //        rootCarArmorStand.getLocation().getZ());

    car.update();

    Location dym = armorStand.getLocation().clone().add(new Vector(0, -1.25, 0));
    SomeShit.particle(dym, EnumParticle.SMOKE_NORMAL);
  }

  @SuppressWarnings("deprecation")
  private void getRelative(Player p, Block b, int x, int z, Car car) {
    if (materialList.contains(b.getRelative(x, 1, z).getType()) ||
            materialList.contains(b.getRelative(x, 0, z).getType())) {
      return;
    }

    car.getRootArmorStand().setVelocity(p.getLocation().getDirection().setY(0.3));
    car.update();
  }

}
