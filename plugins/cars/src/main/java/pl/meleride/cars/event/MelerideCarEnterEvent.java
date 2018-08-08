package pl.meleride.cars.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import pl.meleride.cars.car.BasicCar;

public class MelerideCarEnterEvent extends Event implements Cancellable {

  private static final HandlerList handlers = new HandlerList();
  private Player player;
  private BasicCar basicCar;
  private boolean cancelled;

  public MelerideCarEnterEvent(Player player, BasicCar basicCar) {
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

  public boolean isCancelled() {
    return cancelled;
  }

  public void setCancelled(boolean x) {
    cancelled = x;
  }

}
