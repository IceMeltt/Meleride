package pl.meleride.cars.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import pl.meleride.cars.car.BasicCar;

public class MelerideCarSpawnEvent extends Event implements Cancellable {

  private static final HandlerList handlers = new HandlerList();
  private Player player;
  private boolean cancelled;
  private BasicCar basicCar;

  public MelerideCarSpawnEvent(Player player, BasicCar basicCar) {
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
