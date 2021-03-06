package pl.meleride.world;

import org.bukkit.plugin.java.JavaPlugin;

import pl.meleride.world.impl.runnable.PlayerCheckAboveRunnable;
import pl.meleride.world.impl.runnable.WeatherUpdaterRunnable;
import pl.meleride.world.impl.weather.Weather;

public class MelerideWorld extends JavaPlugin {

  private Weather weather;

  @Override
  public void onEnable() {
    getLogger().info("Trwa uruchamianie schedulerow...");
    weather = new Weather();

    new WeatherUpdaterRunnable(this).runTaskTimerAsynchronously(this, 0, 10 * 60 * 20);
    new PlayerCheckAboveRunnable(this).runTaskTimerAsynchronously(this, 0, 60 * 20);
  }

  @Override
  public void onDisable() {
    getLogger().info("Zamykanie wszelkich schedulerow...");
    getServer().getScheduler().cancelAllTasks();
  }

  public Weather getWeatherInstance() {
    return this.weather;
  }

}
