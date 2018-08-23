package pl.meleride.world.impl.weather.events;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.meleride.world.impl.weather.WeatherChangedEvent;

public class ChangeWeatherEvent implements Listener {

  @EventHandler
  public void onWeatherChanged(WeatherChangedEvent event) {
    switch (event.getNewWeather().toLowerCase()) {
      case "tropical storm":
      case "isolated thundershowers":
      case "isolated thunderstorms":
      case "scattered thunderstorms":
      case "severe thunderstorms":
      case "thunderstorms":
        for (World world : Bukkit.getWorlds()) {
          world.setStorm(true);
          world.setThundering(true);
          world.setWeatherDuration(Integer.MAX_VALUE);
        }
        break;
      case "mixed rain and snow":
      case "mixed rain and sleet":
      case "mixed snow and sleet":
      case "freezing drizzle":
      case "drizzle":
      case "freezing rain":
      case "showers":
      case "snow flurries":
      case "light snow showers":
      case "blowing snow":
      case "snow":
      case "hail":
      case "sleet":
      case "thundershowers":
      case "snow showers":
      case "scattered showers":
      case "heavy snow":
      case "scattered snow showers":
      case "mixed rain and hail":
        for (World world : Bukkit.getWorlds()) {
          world.setStorm(true);
          world.setThundering(false);
          world.setWeatherDuration(Integer.MAX_VALUE);
        }
        break;
      case "hurricane":
      case "tornado":
      case "dust":
      case "foggy":
      case "haze":
      case "smoky":
      case "blustery":
      case "windy":
      case "cold":
      case "cloudy":
      case "mostly cloudy (night)":
      case "mostly cloudy (day)":
      case "partly cloudy (night)":
      case "partly cloudy (day)":
      case "clear (night)":
      case "sunny":
      case "fair (night)":
      case "fair (day)":
      case "hot":
      case "partly cloudy":
      case "not available":
        for (World world : Bukkit.getWorlds()) {
          world.setStorm(false);
          world.setThundering(false);
          world.setWeatherDuration(Integer.MAX_VALUE);
        }
        break;
    }
  }

}
