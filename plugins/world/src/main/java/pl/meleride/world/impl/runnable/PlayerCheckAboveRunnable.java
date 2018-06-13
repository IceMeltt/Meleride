package pl.meleride.world.impl.runnable;

import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.meleride.api.i18n.MessageBundler;
import pl.meleride.api.message.MessageType;
import pl.meleride.api.user.User;
import pl.meleride.api.user.manager.UserManager;
import pl.meleride.api.user.status.DiseaseStatus;
import pl.meleride.world.MelerideWorld;
import pl.meleride.world.impl.weather.Weather;

public class PlayerCheckAboveRunnable extends BukkitRunnable {

  private final MelerideWorld instance;
  private UserManager manager;
  private User user;
  private Weather weather;

  public PlayerCheckAboveRunnable(MelerideWorld instance) {
    this.instance = instance;

    runTaskTimerAsynchronously(instance, 0, 60 * 20);
  }

  @Override
  public void run() {
    Location location;
    World world;
    weather = instance.getWeatherInstance();

    if (weather.getNewerForecast().equalsIgnoreCase("hurricane")
        || weather.getNewerForecast().contains("storm")
        || weather.getNewerForecast().contains("thunder")
        || weather.getNewerForecast().contains("rain")
        || weather.getNewerForecast().contains("snow")) {

      for (Player player : Bukkit.getOnlinePlayers()) {
        location = player.getLocation();
        world = player.getLocation().getWorld();

        if (world.getHighestBlockAt(location).getY() < player.getEyeLocation().getY()) {
          if(player.getInventory().getChestplate() == null) {
            this.freezingConditions(player);
          }
        }
      }
    }
  }

  private void freezingConditions(Player player) {
    ThreadLocalRandom random = ThreadLocalRandom.current();
    this.user = this.manager.getUser(player).get();

    if(!(user.hasDisease(DiseaseStatus.FEVER))) {
      int rnd = random.nextInt(100);
      if(rnd <= 10) {
        user.addDisease(DiseaseStatus.FEVER);

        MessageBundler.create("disease.fever.title").target(MessageType.TITLE).sendTo(player);
        MessageBundler.create("disease.fever.subtitle").target(MessageType.SUB_TITLE).sendTo(player);
        MessageBundler.create("disease.fever.message").target(MessageType.CHAT).sendTo(player);
      }
    }
  }

}
