package pl.meleride.economy.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.meleride.commands.CommandInfo;
import pl.meleride.commands.context.CommandContext;
import pl.meleride.economy.MelerideEconomy;
import pl.meleride.economy.currency.Currency;
import pl.meleride.economy.econplayer.EconPlayer;
import pl.meleride.economy.econplayer.EconPlayerManager;
import pl.meleride.economy.util.ColorUtils;

import java.util.Arrays;
import java.util.Optional;

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
    Optional<EconPlayer> optionalEconPlayer = this.plugin.getEconPlayerManager().getPlayer(player.getUniqueId());

    if (!optionalEconPlayer.isPresent()) {
      return;
    }

    EconPlayer econPlayer = optionalEconPlayer.get();

    if (context.getArgs().length == 0) {
      player.sendMessage(ColorUtils.colorize("&8> &fW portfelu posiadasz&8:"));
      econPlayer.getPocketBalance().forEach((currency, balance) -> player.sendMessage(
              ColorUtils.colorize("&8> &e" + currency.name() + " &8- &f" + balance)
      ));
      return;
    }

    switch (context.getParam(0)) {
      case "status":
        Arrays.stream(Currency.values()).forEach(currency -> {
          player.sendMessage(ColorUtils.colorize(currency.getFullName() +
                  " (" + currency.name() +
                  " " + currency.getSign() +
                  ") - " + currency.getExchangeRate() +
                  " " + currency.getTendency().getSign())
          );
        });
        break;
      case "balance":
        econPlayer.getPocketBalance().forEach((currency, balance) -> player.sendMessage(
                ColorUtils.colorize("&8> &e" + currency.name() + " &8- &f" + balance)
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
        econPlayer.add(Currency.getCurrency(currencyName), amount);
        break;
    }
  }

}
