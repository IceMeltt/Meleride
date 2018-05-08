package pl.meleride.economy.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.meleride.commands.CommandInfo;
import pl.meleride.commands.context.CommandContext;
import pl.meleride.economy.MelerideEconomy;
import pl.meleride.economy.currency.Currency;

import java.util.Arrays;

public class CurrencyCommand {

  private final MelerideEconomy plugin;

  public CurrencyCommand(MelerideEconomy plugin) {
    this.plugin = plugin;
  }

  @CommandInfo(
          name = {"currency", "waluta"},
          description = "Komenda do zarzadzania walutami",
          min = 1,
          userOnly = true,
          permission = "meleride.economy.currency")
  public void teleportCommand(CommandSender sender, CommandContext context) {
    Player player = (Player) sender;

    switch(context.getParam(0)) {
      case "status":
        Arrays.stream(Currency.values())
                .forEach(currency -> {
                  player.sendMessage(currency.getFullName() + "(" + currency.name() + currency.getSign() + ") - " + currency.getExchangeRate() + currency.getTendency().getSign());
        });
        break;
    }
  }
}
