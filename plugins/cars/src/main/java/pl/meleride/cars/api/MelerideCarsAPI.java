package pl.meleride.cars.api;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import pl.meleride.cars.enums.CarType;
import pl.meleride.cars.events.MelerideCarSpawnEvent;
import pl.meleride.cars.objects.Car;

public class MelerideCarsAPI {

	private static Map<UUID, Car> cars = new HashMap<UUID, Car>();
	private static Map<UUID, UUID> seatconnect = new HashMap<UUID, UUID>();

	public void registerPublicCar(Location loc, Car car) {
		
	}

	public void spawnCar(Player p, CarType carType, double max_speed, int durability, int max_passengers, byte model,
			byte brand, Location l) {
		
		ArmorStand c2 = l.getWorld().spawn(l, ArmorStand.class);
		c2.setVisible(false);
		c2.setGravity(true);
		c2.setCustomName("�rAuto");
		c2.setCustomNameVisible(false);
		c2.setFireTicks(0);
		c2.setMarker(false);
		
		Car car = new Car(c2, carType, max_speed, durability, max_passengers, model, brand, l, p);
		
		MelerideCarSpawnEvent event = new MelerideCarSpawnEvent(p, car);
		Bukkit.getServer().getPluginManager().callEvent(event);
		if(event.isCancelled()){
			car.getSeat().remove();
			car.getCar().remove();
			return;
		}
		cars.put(c2.getUniqueId(), car);		
	}

	public static Map<UUID, Car> getCarsMap() {
		return cars;
	}
	
	public static Map<UUID, UUID> getSeatConnect() {
		return seatconnect;
	}

}
