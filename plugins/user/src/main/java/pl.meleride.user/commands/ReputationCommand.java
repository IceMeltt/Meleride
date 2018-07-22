package pl.meleride.user.commands;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.meleride.api.i18n.MessageBundler;
import pl.meleride.api.message.MessageType;
import pl.meleride.commands.CommandInfo;
import pl.meleride.commands.context.CommandContext;
import pl.meleride.commands.util.CommandUtils;
import pl.meleride.user.MelerideUser;
import pl.meleride.user.reputation.ReputationActionHandler;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class ReputationCommand {

  private final MelerideUser plugin;
  private final ReputationActionHandler reputationActionHandler;

  public ReputationCommand(final MelerideUser plugin) {
    this.plugin = plugin;
    this.reputationActionHandler = new ReputationActionHandler(plugin);
  }

  @CommandInfo(
      name = { "rep" },
      description = "Modyfikuj lub wyświetl reputację gracza",
      usage = "<add/view> [value] <player>",
      userOnly = true,
      completer = "reputationCommandCompleter")
  public void reputationCommandHandler(CommandSender sender, CommandContext context) {
    Player senderPlayer = CommandUtils.toPlayer(sender);

    if(context.getParamsLength() == 0 || context.getParamsLength() >= 3) {
      senderPlayer.playSound(senderPlayer.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 50, 1);
      this.reputationActionHandler.viewPlayerReputation(senderPlayer, senderPlayer.getName());
    }

    if(context.getParamsLength() >= 1) {
      switch (context.getParam(0).toLowerCase()) {
        case "view":
          if(context.getParamsLength() == 1) {
            MessageBundler.create("help.reputation")
                .target(MessageType.CHAT)
                .sendTo(senderPlayer);
            return;
          }

          senderPlayer.playSound(senderPlayer.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 50, 5);
          this.reputationActionHandler.viewPlayerReputation(senderPlayer, context.getParam(1)); break;
        case "add":
          if(!senderPlayer.hasPermission("meleride.reputation.admin")) {
            MessageBundler.create("user.noPermission")
                .target(MessageType.CHAT)
                .sendTo(senderPlayer);
            return;
          }

          if(context.getParamsLength() == 1 || context.getParamsLength() == 2) {
            MessageBundler.create("help.reputation")
                .target(MessageType.CHAT)
                .sendTo(senderPlayer);
            return;
          }

          this.reputationActionHandler.addPlayerReputation(senderPlayer, context.getParam(1), Integer.valueOf(context.getParam(2))); break;
        default:
          MessageBundler.create("help.reputation")
              .target(MessageType.CHAT)
              .sendTo(senderPlayer);
          break;
      }
    }
  }

  public List<String> reputationCommandCompleter(CommandSender sender, CommandContext context) {
    if (context.getParamsLength() == 1) {
      return Arrays.asList("view", "add");
    } else if (context.getParamsLength() == 2) {
      return Bukkit.getOnlinePlayers()
          .stream()
          .map(Player::getName)
          .filter(name -> !name.equals(sender.getName()))
          .collect(Collectors.toList());
    }

    return Collections.emptyList();
  }


}
