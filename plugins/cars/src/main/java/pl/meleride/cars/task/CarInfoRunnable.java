package pl.meleride.cars.task;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import pl.meleride.cars.MelerideCars;

public class CarInfoRunnable extends BukkitRunnable {

  private final MelerideCars instance;

  public CarInfoRunnable(MelerideCars instance) {
    this.instance = instance;

    runTaskTimerAsynchronously(this.instance, 20, 20);
  }

  @Override
  public void run() {
    Bukkit.getOnlinePlayers().stream()
            .filter(player -> player.getVehicle() != null)
            .filter(player -> this.instance.getCarManager().getCarBySeat(player.getVehicle().getUniqueId()) != null)
            .forEach(player -> player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("v samohodziku ;~~DD")));
  }

}
