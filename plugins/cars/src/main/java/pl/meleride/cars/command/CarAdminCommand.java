package pl.meleride.cars.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.meleride.cars.MelerideCars;
import pl.meleride.cars.api.MelerideCarsAPI;
import pl.meleride.cars.car.Car;
import pl.meleride.cars.car.CarType;
import pl.meleride.cars.car.impl.LamborghiniCar;

public class CarAdminCommand implements CommandExecutor {

  private final MelerideCars instance;

  public CarAdminCommand(MelerideCars instance) {
    this.instance = instance;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
    Car car = new LamborghiniCar(CarType.NORMAL, ((Player) sender).getUniqueId(), ((Player) sender).getLocation());
    car.update();

    this.instance.getCarManager().addCar(car);

    /*MelerideCarsAPI api = new MelerideCarsAPI();
    api.spawnCar((Player) sender, CarType.NORMAL, Integer.parseInt(args[0]), 105, 1, (byte) 1, (byte) 1, ((Player) sender).getLocation().add(1, 0, 1));
    */
    return true;
  }

}
