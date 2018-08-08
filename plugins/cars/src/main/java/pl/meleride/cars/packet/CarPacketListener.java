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

import pl.meleride.cars.api.MelerideCarsAPI;
import pl.meleride.cars.objects.Car;
import pl.meleride.cars.util.SomeShit;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayInSteerVehicle;

public class CarPacketListener extends PacketAdapter {

  private final double ROTATION_SPEED = 1.5;

  public CarPacketListener(Plugin plugin, ListenerPriority listenerPriority, PacketType[] types) {
    super(plugin, listenerPriority, types);
  }

  public void onPacketReceiving(PacketEvent e) {
    if (e.getPacketType() == PacketType.Play.Client.STEER_VEHICLE
            && e.getPlayer().getVehicle() instanceof ArmorStand) {

      PacketPlayInSteerVehicle packet = (PacketPlayInSteerVehicle) e.getPacket().getHandle();
      PacketContainer pc = e.getPacket();

      ArmorStand something = (ArmorStand) e.getPlayer().getVehicle();

      if (!MelerideCarsAPI.getSeatConnect().containsKey(something.getUniqueId())) return;
      if (!MelerideCarsAPI.getCarsMap().containsKey(MelerideCarsAPI.getSeatConnect().get(something.getUniqueId())))
        return;
      Car car = MelerideCarsAPI.getCarsMap().get(MelerideCarsAPI.getSeatConnect().get(something.getUniqueId()));
      ArmorStand seat = car.getSeat();
      ArmorStand car2 = car.getCar();

      if (!(seat.getPassenger() instanceof Player)) {
        return;
      }

      Player p = (Player) seat.getPassenger();
      Block b = car2.getLocation().getBlock();

      // PAKIECIKI
      float forward = pc.getFloat().read(1).floatValue(); // RUCH PRZOD/TYL
      float sideways = packet.a(); // RUCH NA BOKI
      boolean space = packet.c(); // SPACJA

      if (forward > 0) { // [W]
        if (!(MelerideCarsAPI.getCarsMap().get(car2.getUniqueId()).getSpeed() >= 1)) {
          MelerideCarsAPI.getCarsMap().get(car2.getUniqueId()).setSpeed(MelerideCarsAPI.getCarsMap().get(car2.getUniqueId()).getSpeed() + 0.01);
        }
      } else if (forward < 0) { // [S]
        if (!(MelerideCarsAPI.getCarsMap().get(car2.getUniqueId()).getSpeed() <= -1)) {
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
      if ((b.getRelative(1, 1, 0).getTypeId() == 0 && b.getRelative(1, 0, 0).getTypeId() != 0)) {
        getRelative(p, b, 1, 0, car2, seat);
      }
      // CHECK -X AXIS
      if ((b.getRelative(-1, 1, 0).getTypeId() == 0 && b.getRelative(-1, 0, 0).getTypeId() != 0)) {
        getRelative(p, b, -1, 0, car2, seat);
      }
      // CHECK Z AXIS
      if ((b.getRelative(0, 1, 1).getTypeId() == 0 && b.getRelative(0, 0, 1).getTypeId() != 0)) {
        getRelative(p, b, 0, 1, car2, seat);
      }
      // CHECK -Z AXIS
      if ((b.getRelative(0, 1, -1).getTypeId() == 0 && b.getRelative(0, 0, -1).getTypeId() != 0)) {
        getRelative(p, b, 0, -1, car2, seat);
      }
      ((CraftArmorStand) seat).getHandle().setPosition(car2.getLocation().getX(), car2.getLocation().getY(),
              car2.getLocation().getZ());

      Location dym = seat.getLocation().add(seat.getLocation().getDirection().multiply(-2));
      SomeShit.particle(dym, EnumParticle.SMOKE_NORMAL);
    }
  }

  @SuppressWarnings("deprecation")
  private void getRelative(Player p, Block b, int X, int Z, ArmorStand car, ArmorStand seat) {
    if (b.getRelative(X, 1, Z).getTypeId() == 38 || b.getRelative(X, 1, Z).getTypeId() == 31
            || b.getRelative(X, 1, Z).getTypeId() == 175
            || b.getRelative(X, 1, Z).getType().equals(Material.SUGAR_CANE_BLOCK)
            || b.getRelative(X, 1, Z).getTypeId() == 39 || b.getRelative(X, 1, Z).getTypeId() == 40
            || b.getRelative(X, 1, Z).getTypeId() == 37 ||

            b.getRelative(X, 0, Z).getTypeId() == 38 || b.getRelative(X, 0, Z).getTypeId() == 31
            || b.getRelative(X, 0, Z).getTypeId() == 175
            || b.getRelative(X, 0, Z).getType().equals(Material.SUGAR_CANE_BLOCK)
            || b.getRelative(X, 0, Z).getTypeId() == 39 || b.getRelative(X, 0, Z).getTypeId() == 37
            || b.getRelative(X, 0, Z).getTypeId() == 40) {
      return;

    }
    car.setVelocity(p.getLocation().getDirection().setY(0.3));
    ((CraftArmorStand) seat).getHandle().setPosition(car.getLocation().getX(), car.getLocation().getY(),
            car.getLocation().getZ());
    return;
  }

}
