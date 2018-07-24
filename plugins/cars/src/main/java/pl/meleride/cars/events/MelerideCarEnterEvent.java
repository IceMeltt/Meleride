package pl.meleride.cars.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import pl.meleride.cars.objects.Car;

public class MelerideCarEnterEvent extends Event implements Cancellable {
	
	private static final HandlerList handlers = new HandlerList();
	private Player player;
	private boolean cancelled;
	private Car car;
	
	public MelerideCarEnterEvent(Player player, Car car){
		this.player = player;
		this.car = car;
		cancelled = false;
	}
	
	public Car getCar(){
		return car;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	@Override
	public HandlerList getHandlers(){
		return handlers;
	}
	
	public static HandlerList getHandlerList(){
	    return handlers;
	}
	
	public HandlerList getHandler(){
		return handlers;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean x) {
		cancelled = x;
	}

}
