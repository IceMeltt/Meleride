package pl.meleride.cars.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import pl.meleride.cars.car.BasicCar;

public class MelerideCarExitEvent extends Event {

  private static final HandlerList handlers = new HandlerList();
  private Player player;
  private BasicCar basicCar;

  public MelerideCarExitEvent(Player player, BasicCar basicCar) {
    this.player = player;
    this.basicCar = basicCar;
  }

  public BasicCar getBasicCar() {
    return basicCar;
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
