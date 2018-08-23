package pl.meleride.world.impl.weather;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class WeatherChangedEvent extends Event implements Cancellable {
  
  private static final HandlerList handlers = new HandlerList();
  private boolean cancelled;
  private String oldWeather;
  private String newWeather;
  
  public WeatherChangedEvent(String oldWeather, String newWeather) {
    this.oldWeather = oldWeather;
    this.newWeather = newWeather;
  }
  
  public String getOldWeather() {
    return oldWeather;
  }
  
  public String getNewWeather() {
    return newWeather;
  }
  
  @Override
  public boolean isCancelled() {
    return this.cancelled;
  }
  
  @Override
  public void setCancelled(boolean cancel) {
    this.cancelled = cancel;
  }
  
  @Override
  public HandlerList getHandlers() {
    return handlers;
  }
  
  public static HandlerList getHandlerList() {
    return handlers;
  }
  
}
