package pl.meleride.cars;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;

import pl.meleride.cars.commands.CarAdminCommand;
import pl.meleride.cars.listeners.SecondCarListener;
import pl.meleride.cars.packet.CarPacketListener;
import pl.meleride.cars.tasks.PublicCarsControllSystem;

public class Main extends JavaPlugin {

	private static Main instance;

	@Override
	public void onEnable() {
		instance = this;
		regListeners();
		new PublicCarsControllSystem().runTaskTimerAsynchronously(this, 0, 20 * 30);
		getCommand("ca").setExecutor(new CarAdminCommand());
	}

	@Override
	public void onDisable() {

	}

	private void regListeners() {
		ProtocolLibrary.getProtocolManager().addPacketListener(new CarPacketListener(this, ListenerPriority.NORMAL,
				new PacketType[] { PacketType.Play.Client.STEER_VEHICLE }));
		Bukkit.getServer().getPluginManager().registerEvents(new SecondCarListener(), this);
	}

	public static Main getInstance() {
		return instance;
	}

}
