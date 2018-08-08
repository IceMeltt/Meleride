package pl.meleride.cars.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;

public class SomeShit {

  public static void particle(Location loc, EnumParticle en) {
    PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(en, true, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), 0, 0, 0, 0, 1);
    for (Player p : Bukkit.getOnlinePlayers()) {
      ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }
  }

}
