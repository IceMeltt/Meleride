package pl.meleride.cars.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import pl.meleride.cars.api.MelerideCarsAPI;
import pl.meleride.cars.enums.CarType;

import pl.meleride.cars.events.MelerideCarEnterEvent;
import pl.meleride.cars.events.MelerideCarExitEvent;
import pl.meleride.cars.objects.Car;

public class SecondCarListener implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onEnterVehicle(PlayerInteractAtEntityEvent e) {
		if (e.getRightClicked() instanceof ArmorStand) {
			ArmorStand as = (ArmorStand) e.getRightClicked();
			if (MelerideCarsAPI.getCarsMap().containsKey(as.getUniqueId())) {
				Car car = MelerideCarsAPI.getCarsMap().get(as.getUniqueId());
				MelerideCarEnterEvent event = new MelerideCarEnterEvent(e.getPlayer(), car);
				Bukkit.getPluginManager().callEvent(event);
				if (event.isCancelled())
					return;
				if(car.getCarType() != CarType.PUBLIC){
					if(car.getOwner() != e.getPlayer()) return;
				}
				car.getSeat().setPassenger(e.getPlayer());
			} else {
				return;
			}
		}

	}

	// BLOKADA BUGOWANIA AUT
	@EventHandler
	public void onArmorStandManipulate(PlayerArmorStandManipulateEvent e) {
		if (MelerideCarsAPI.getCarsMap().containsKey(e.getRightClicked().getUniqueId()))
			e.setCancelled(true);
		return;
	}

	@EventHandler
	public void onEnterCar(MelerideCarEnterEvent e) {

	}

	//DOTYCZY SYSTEMU KONTROLI PUBLICZNYCH AUT
	@EventHandler
	public void onExitCar(MelerideCarExitEvent e) {
		if (e.getCar().getCarType() == CarType.PUBLIC) {
			e.getCar().setLastExit(System.currentTimeMillis());
		}
	}

}
