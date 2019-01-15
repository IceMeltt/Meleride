package pl.meleride.world.impl.weather.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import pl.meleride.world.impl.weather.types.WeatherType;

public class WeatherChangedEvent extends Event implements Cancellable {
  
  private static final HandlerList handlers = new HandlerList();
  private boolean cancelled;
  private WeatherType oldWeather;
  private WeatherType newWeather;
  
  public WeatherChangedEvent(WeatherType oldWeather, WeatherType newWeather) {
    this.oldWeather = oldWeather;
    this.newWeather = newWeather;
  }
  
  public WeatherType getOldWeather() {
    return oldWeather;
  }
  
  public WeatherType getNewWeather() {
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
