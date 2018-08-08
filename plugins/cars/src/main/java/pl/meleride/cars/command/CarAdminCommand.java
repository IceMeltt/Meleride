package pl.meleride.cars.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.meleride.cars.api.MelerideCarsAPI;
import pl.meleride.cars.car.CarType;

public class CarAdminCommand implements CommandExecutor {

  public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
    MelerideCarsAPI api = new MelerideCarsAPI();
    api.spawnCar((Player) sender, CarType.NORMAL, 500, 105, 1, (byte) 1, (byte) 1, ((Player) sender).getLocation().add(1, 0, 1));
    return true;
  }

}
