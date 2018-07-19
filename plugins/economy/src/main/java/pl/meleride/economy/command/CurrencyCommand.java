package pl.meleride.economy.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.meleride.commands.CommandInfo;
import pl.meleride.commands.context.CommandContext;
import pl.meleride.economy.MelerideEconomy;
import pl.meleride.economy.currency.Currency;
import static pl.meleride.api.message.MessageUtil.colored;

import java.util.Arrays;
import pl.meleride.economy.entity.User;

public class CurrencyCommand {

  private final MelerideEconomy plugin;

  public CurrencyCommand(MelerideEconomy plugin) {
    this.plugin = plugin;
  }

  @CommandInfo(
          name = {"wallet", "portfel"},
          description = "Komenda do zarzadzania walutami",
          userOnly = true,
          permission = "meleride.economy.currency")
  public void walletCommand(CommandSender sender, CommandContext context) {
    Player player = (Player) sender;

    User user = this.plugin.getUserManager().getUser(player).get();

    if (context.getArgs().length == 0) {
      user.add(Currency.PLN, 11);
      player.sendMessage(colored("&8> &fW portfelu posiadasz&8:"));
      user.getPocketBalance().forEach((currency, balance) -> player.sendMessage(
              colored("&8> &e" + currency.name() + " &8- &f" + balance)
      ));
      return;
    }

    switch (context.getParam(0)) {
      case "status":
        Arrays.stream(Currency.values()).forEach(currency -> {
          player.sendMessage(colored(currency.getFullName() +
                  " (" + currency.name() +
                  " " + currency.getSign() +
                  ") - " + currency.getExchangeRate() +
                  " " + currency.getTendency().getSign())
          );
        });
        break;
      case "balance":
        user.getPocketBalance().forEach((currency, balance) -> player.sendMessage(
                colored("&8> &e" + currency.name() + " &8- &f" + balance)
        ));
        break;
      case "give":
        if (!player.isOp()) {
          break;
        }

        if (context.getArgs().length != 3) {
          break;
        }

        String currencyName = context.getParam(2);
        if (Currency.getCurrency(currencyName) == null) {
          return;
        }

        double amount = context.getParamDouble(1);
        user.add(Currency.getCurrency(currencyName), amount);
        break;
    }
  }

}
