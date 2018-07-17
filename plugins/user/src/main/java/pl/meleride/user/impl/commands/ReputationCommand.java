package pl.meleride.user.impl.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.meleride.api.i18n.MessageBundler;
import pl.meleride.api.message.MessageType;
import pl.meleride.commands.CommandInfo;
import pl.meleride.commands.context.CommandContext;
import pl.meleride.commands.util.CommandUtils;
import pl.meleride.user.MelerideUser;
import pl.meleride.user.impl.reputation.ReputationActionHandler;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class ReputationCommand {

  private final MelerideUser instance;
  private ReputationActionHandler reputationActionHandler;

  public ReputationCommand(final MelerideUser instance) {
    this.instance = instance;
    this.reputationActionHandler = new ReputationActionHandler(instance);
  }

  @CommandInfo(
      name = { "rep" },
      description = "Modyfikuj lub wyświetl reputację gracza",
      min = 2,
      usage = "<add/view> [value] <player>",
      userOnly = true,
      permission = "meleride.reputation.admin",
      completer = "reputationCommandCompleter")
  public void reputationCommandHandler(CommandSender sender, CommandContext context) {
    Player senderPlayer = CommandUtils.toPlayer(sender);

    switch (context.getParam(0).toLowerCase()) {
      case "view":
        this.reputationActionHandler.viewPlayerReputation(senderPlayer, context.getParam(1)); break;
      case "add":
        this.reputationActionHandler.addPlayerReputation(senderPlayer, context.getParam(1), Integer.valueOf(context.getParam(2))); break;
      default:
        MessageBundler.create("user.invalidSubCommand")
            .target(MessageType.CHAT)
            .sendTo(senderPlayer);
        break;
    }
  }

  public List<String> reputationCommandCompleter(CommandSender sender, CommandContext context) {
    if (context.getParamsLength() == 1) {
      return Arrays.asList("view", "add");
    } else if (context.getParamsLength() == 2) {
      return this.instance.getServer().getOnlinePlayers()
          .stream()
          .map(Player::getName)
          .filter(name -> !name.equals(sender.getName()))
          .collect(Collectors.toList());
    }

    return Collections.emptyList();
  }


}
