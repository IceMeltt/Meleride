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
import pl.meleride.cars.api.MelerideCarsAPI;
import pl.meleride.cars.car.BasicCar;
import pl.meleride.cars.util.SomeShit;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayInSteerVehicle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CarPacketListener extends PacketAdapter {

  private final double ROTATION_SPEED = 2;
  private final List<Material> materialList = new ArrayList<>();

  public CarPacketListener(Plugin plugin, ListenerPriority listenerPriority, PacketType[] types) {
    super(plugin, listenerPriority, types);

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

    PacketPlayInSteerVehicle packet = (PacketPlayInSteerVehicle) event.getPacket().getHandle();
    PacketContainer pc = event.getPacket();

    ArmorStand something = (ArmorStand) event.getPlayer().getVehicle();

    if (!MelerideCarsAPI.getSeatConnect().containsKey(something.getUniqueId())) return;
    if (!MelerideCarsAPI.getCarsMap().containsKey(MelerideCarsAPI.getSeatConnect().get(something.getUniqueId())))
      return;
    BasicCar basicCar = MelerideCarsAPI.getCarsMap().get(MelerideCarsAPI.getSeatConnect().get(something.getUniqueId()));
    ArmorStand seat = basicCar.getSeat();
    ArmorStand car2 = basicCar.getCar();

    if (!(seat.getPassengers().get(0) instanceof Player)) {
      return;
    }

    Player p = (Player) seat.getPassengers().get(0);
    Block b = car2.getLocation().getBlock();

    // PAKIECIKI
    float forward = pc.getFloat().read(1); // RUCH PRZOD/TYL
    float sideways = packet.a(); // RUCH NA BOKI
    boolean space = packet.c(); // SPACJA

    if (forward > 0) { // [W]
      if (!(MelerideCarsAPI.getCarsMap().get(car2.getUniqueId()).getSpeed() >= 2)) {
        MelerideCarsAPI.getCarsMap().get(car2.getUniqueId()).setSpeed(MelerideCarsAPI.getCarsMap().get(car2.getUniqueId()).getSpeed() + 0.01);
      }
    } else if (forward < 0) { // [S]
      if (!(MelerideCarsAPI.getCarsMap().get(car2.getUniqueId()).getSpeed() <= -2)) {
        MelerideCarsAPI.getCarsMap().get(car2.getUniqueId()).setSpeed(MelerideCarsAPI.getCarsMap().get(car2.getUniqueId()).getSpeed() - 0.01);
      }
				/*if (MelerideCarsAPI.getCarsMap().get(car2.getUniqueId()).getSpeed() >= 0.1) {
					MelerideCarsAPI.getCarsMap().get(car2.getUniqueId()).setSpeed(MelerideCarsAPI.getCarsMap().get(car2.getUniqueId()).getSpeed()-0.01);
				} else {
					MelerideCarsAPI.getCarsMap().get(car2.getUniqueId()).setSpeed(0);
				}*/
    }

    //HAMOWANKO
    if (space) {
      if (MelerideCarsAPI.getCarsMap().get(car2.getUniqueId()).getSpeed() != 0) {
        if (MelerideCarsAPI.getCarsMap().get(car2.getUniqueId()).getSpeed() > 0) {
          if (MelerideCarsAPI.getCarsMap().get(car2.getUniqueId()).getSpeed() - 0.04 < 0) {
            MelerideCarsAPI.getCarsMap().get(car2.getUniqueId()).setSpeed(0);
          } else {
            MelerideCarsAPI.getCarsMap().get(car2.getUniqueId()).setSpeed(MelerideCarsAPI.getCarsMap().get(car2.getUniqueId()).getSpeed() - 0.04);
          }
        } else if (MelerideCarsAPI.getCarsMap().get(car2.getUniqueId()).getSpeed() < 0) {
          if (MelerideCarsAPI.getCarsMap().get(car2.getUniqueId()).getSpeed() + 0.04 > 0) {
            MelerideCarsAPI.getCarsMap().get(car2.getUniqueId()).setSpeed(0);
          } else {
            MelerideCarsAPI.getCarsMap().get(car2.getUniqueId()).setSpeed(MelerideCarsAPI.getCarsMap().get(car2.getUniqueId()).getSpeed() + 0.04);
          }
        }
      }
    }

    if (sideways > 0) { // Side[A]
      if (car2.getVelocity().length() > 0.1) {
        ((CraftArmorStand) car2).getHandle().yaw = (float) (car2.getLocation().getYaw() + (ROTATION_SPEED * -1));
        ((CraftArmorStand) seat).getHandle().yaw = (float) (car2.getLocation().getYaw() + (ROTATION_SPEED * -1));
      }

    } else if (sideways < 0) { // Side[D]
      if (car2.getVelocity().length() > 0.1) {
        ((CraftArmorStand) car2).getHandle().yaw = (float) (car2.getLocation().getYaw() + ROTATION_SPEED);
        ((CraftArmorStand) seat).getHandle().yaw = (float) (car2.getLocation().getYaw() + ROTATION_SPEED);

      }
    }

    car2.setVelocity(car2.getLocation().getDirection().multiply(MelerideCarsAPI.getCarsMap().get(car2.getUniqueId()).getSpeed()).setY(-2));
    seat.setVelocity(p.getLocation().getDirection().setY(-2));

    ((CraftArmorStand) seat).getHandle().setPosition(car2.getLocation().getX(), car2.getLocation().getY(),
            car2.getLocation().getZ());

    // CHECK X AXIS
    if ((b.getRelative(1, 1, 0).getType() == Material.AIR && b.getRelative(1, 0, 0).getType() != Material.AIR)) {
      getRelative(p, b, 1, 0, car2, seat);
    }
    // CHECK -X AXIS
    if ((b.getRelative(-1, 1, 0).getType() == Material.AIR && b.getRelative(-1, 0, 0).getType() != Material.AIR)) {
      getRelative(p, b, -1, 0, car2, seat);
    }
    // CHECK Z AXIS
    if ((b.getRelative(0, 1, 1).getType() == Material.AIR && b.getRelative(0, 0, 1).getType() != Material.AIR)) {
      getRelative(p, b, 0, 1, car2, seat);
    }
    // CHECK -Z AXIS
    if ((b.getRelative(0, 1, -1).getType() == Material.AIR && b.getRelative(0, 0, -1).getType() != Material.AIR)) {
      getRelative(p, b, 0, -1, car2, seat);
    }

    ((CraftArmorStand) seat).getHandle().setPosition(car2.getLocation().getX(), car2.getLocation().getY(),
            car2.getLocation().getZ());

    Location dym = seat.getLocation().clone().add(new Vector(0, -1.25, 0));
    SomeShit.particle(dym, EnumParticle.SMOKE_NORMAL);
  }

  @SuppressWarnings("deprecation")
  private void getRelative(Player p, Block b, int x, int z, ArmorStand car, ArmorStand seat) {
    if (materialList.contains(b.getRelative(x, 1, z).getType()) ||
            materialList.contains(b.getRelative(x, 0, z).getType())) {
      return;
    }

    car.setVelocity(p.getLocation().getDirection().setY(0.3));
    ((CraftArmorStand) seat).getHandle().setPosition(car.getLocation().getX(), car.getLocation().getY(),
            car.getLocation().getZ());
  }

}
