package pl.meleride.world.impl.runnable;

import org.bukkit.scheduler.BukkitRunnable;
import pl.meleride.world.MelerideWorld;
import pl.meleride.world.impl.weather.Weather;

public class WeatherUpdaterRunnable extends BukkitRunnable {

  private final MelerideWorld instance;

  public WeatherUpdaterRunnable(MelerideWorld instance) {
    this.instance = instance;
  }

  @Override
  public void run() {
    this.instance.getLogger().info("Trwa aktualizacja pogody...");
    try {
      Weather weather = this.instance.getWeatherInstance();
      weather.updateWeather();
    } catch (Exception ex) {
      this.instance.getLogger().severe("Wystapil nieprzewidziany blad przy aktualizowaniu pogody!");
    }
  }

}
