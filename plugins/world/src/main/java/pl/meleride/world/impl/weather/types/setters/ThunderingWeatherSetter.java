package pl.meleride.world.impl.weather.types.setters;

import org.bukkit.Bukkit;
import org.bukkit.World;

public final class ThunderingWeatherSetter implements WeatherSetter {

  @Override
  public void setWeather() {
    for (World world : Bukkit.getWorlds()) {
      world.setThundering(true);
      world.setStorm(true);
      world.setWeatherDuration(Integer.MAX_VALUE);
    }
  }

}
