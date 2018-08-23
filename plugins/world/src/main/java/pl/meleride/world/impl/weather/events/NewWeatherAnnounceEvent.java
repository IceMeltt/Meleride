package pl.meleride.world.impl.weather.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import pl.meleride.api.i18n.MessageBundler;
import pl.meleride.api.message.MessageType;
import pl.meleride.world.impl.weather.Weather;
import pl.meleride.world.impl.weather.WeatherChangedEvent;

public class NewWeatherAnnounceEvent implements Listener {
  
  @EventHandler(priority = EventPriority.LOWEST)
  public void onWeatherChanged(WeatherChangedEvent event) {
    MessageBundler.create("forecast.newforecast")
        .withField("FORECAST", Weather.translate(event.getNewWeather()))
        .target(MessageType.CHAT)
        .sendTo(Bukkit.getOnlinePlayers());
  }
  
}
