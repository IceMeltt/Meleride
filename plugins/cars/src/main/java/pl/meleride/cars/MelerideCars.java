package pl.meleride.cars;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;

import pl.meleride.cars.command.CarAdminCommand;
import pl.meleride.cars.listener.SecondCarListener;
import pl.meleride.cars.packet.CarPacketListener;
import pl.meleride.cars.task.PublicCarsControllSystem;

public class MelerideCars extends JavaPlugin {

  @Override
  public void onEnable() {
    registerListeners();
    new PublicCarsControllSystem().runTaskTimerAsynchronously(this, 0, 600);
    getCommand("ca").setExecutor(new CarAdminCommand());
  }

  private void registerListeners() {
    ProtocolLibrary.getProtocolManager().addPacketListener(new CarPacketListener(this, ListenerPriority.NORMAL,
            new PacketType[]{PacketType.Play.Client.STEER_VEHICLE}));
    Bukkit.getServer().getPluginManager().registerEvents(new SecondCarListener(), this);
  }

}
