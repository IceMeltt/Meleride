package pl.meleride.world;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import pl.meleride.world.impl.runnable.PlayerCheckAboveRunnable;
import pl.meleride.world.impl.runnable.WeatherUpdaterRunnable;
import pl.meleride.world.impl.weather.Weather;
import pl.meleride.world.impl.weather.events.ChangeWeatherEvent;
import pl.meleride.world.impl.weather.events.NewWeatherAnnounceEvent;

public class MelerideWorld extends JavaPlugin {

  private Weather weather;

  @Override
  public void onEnable() {
    getLogger().info("Trwa uruchamianie schedulerow...");
    weather = new Weather();
    
    registerListener(
        new ChangeWeatherEvent(),
        new NewWeatherAnnounceEvent()
    );

    new WeatherUpdaterRunnable(this).runTaskTimerAsynchronously(this, 0, 10 * 60 * 20);
    new PlayerCheckAboveRunnable(this).runTaskTimerAsynchronously(this, 0, 60 * 20);
  }

  @Override
  public void onDisable() {
    getLogger().info("Zamykanie wszelkich schedulerow...");
    getServer().getScheduler().cancelAllTasks();
  }
  
  public void registerListener(Listener... listeners) {
    for (Listener listener : listeners)
      this.getServer().getPluginManager().registerEvents(listener, this);
  }

  public Weather getWeatherInstance() {
    return this.weather;
  }

}
