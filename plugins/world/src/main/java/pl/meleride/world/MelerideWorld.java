package pl.meleride.world;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import pl.meleride.world.impl.runnable.WeatherUpdaterRunnable;
import pl.meleride.world.impl.weather.Weather;

public class MelerideWorld extends JavaPlugin implements CommandExecutor {

  private Weather weather;

  @Override
  public void onEnable() {
    getLogger().info("Trwa uruchamianie schedulerow...");
    weather = new Weather();

    new WeatherUpdaterRunnable(this);

    getCommand("pogoda").setExecutor(this);
  }

  @Override
  public void onDisable() {
    getLogger().info("Zamykanie wszelkich schedulerow...");
    getServer().getScheduler().cancelAllTasks();
  }

  //TODO Usunąć w późniejszym czasie
  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    Weather weather = this.getWeatherInstance();
    sender.sendMessage("Aktualna pogoda: " + weather.getTemperature());
    sender.sendMessage("Forecast: " + weather.getForecast());
    return false;
  }

  public Weather getWeatherInstance() {
    return weather;
  }

}
