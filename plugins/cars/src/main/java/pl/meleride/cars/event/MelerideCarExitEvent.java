package pl.meleride.cars.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import pl.meleride.cars.car.Car;

public class MelerideCarExitEvent extends Event {

  private static final HandlerList handlers = new HandlerList();
  private Player player;
  private Car car;

  public MelerideCarExitEvent(Player player, Car car) {
    this.player = player;
    this.car = car;
  }

  public Car getCar() {
    return car;
  }

  public Player getPlayer() {
    return player;
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

  public HandlerList getHandler() {
    return handlers;
  }

}
