package pl.meleride.cars;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;

import pl.meleride.cars.car.CarManager;
import pl.meleride.cars.command.CarAdminCommand;
import pl.meleride.cars.listener.PlayerArmorStandManipulateListener;
import pl.meleride.cars.listener.PlayerInteractAtEntityListener;
import pl.meleride.cars.listener.SecondCarListener;
import pl.meleride.cars.packet.CarPacketListener;
import pl.meleride.cars.task.PublicCarsControllSystem;

public class MelerideCars extends JavaPlugin {

  private CarManager carManager;

  @Override
  public void onEnable() {
    this.carManager = new CarManager();

    ProtocolLibrary.getProtocolManager().addPacketListener(new CarPacketListener(this, ListenerPriority.NORMAL,
            new PacketType[]{PacketType.Play.Client.STEER_VEHICLE}));
    registerListeners(
            new PlayerArmorStandManipulateListener(this),
            new PlayerInteractAtEntityListener(this),
            new SecondCarListener()
    );

    new PublicCarsControllSystem().runTaskTimerAsynchronously(this, 0, 600);

    getCommand("ca").setExecutor(new CarAdminCommand(this));
  }

  public CarManager getCarManager() {
    return carManager;
  }

  private void registerListeners(Listener... listeners) {
    PluginManager pluginManager = Bukkit.getPluginManager();
    for (Listener listener : listeners) {
      pluginManager.registerEvents(listener, this);
    }
  }

}
